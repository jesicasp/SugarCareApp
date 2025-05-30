package com.pa.sugarcare.utility

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.SystemClock
import android.util.Log
import com.pa.sugarcare.R
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp

class ImageClassifierHelper(
    var threshold: Float = 0.1f,
    var maxResults: Int = 3,
    val modelName: String = "product_model_mobilenet.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?
) {

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }

    fun classifyImage(imageUri: Uri) {
        Log.d(TAG, "Starting classifyImage with URI: $imageUri")

        try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            if (inputStream == null) {
                Log.e(TAG, "Failed to open InputStream from URI: $imageUri")
                classifierListener?.onError("Cannot open image input stream")
                return
            }

            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            if (bitmap == null) {
                Log.e(TAG, "Failed to decode Bitmap from URI: $imageUri")
                classifierListener?.onError("Cannot decode image bitmap")
                return
            }

            Log.d(TAG, "Bitmap decoded successfully: width=${bitmap.width}, height=${bitmap.height}")

            val imageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                .add(NormalizeOp(0f, 255f))  // Sesuaikan jika model butuh normalisasi lain
                .build()

            val tensorImage = imageProcessor.process(TensorImage.fromBitmap(bitmap))
            Log.d(TAG, "TensorImage created: shape=${tensorImage.tensorBuffer.shape.contentToString()}, dtype=${tensorImage.tensorBuffer.dataType}")

            val modelBuffer = FileUtil.loadMappedFile(context, modelName)
            Log.d(TAG, "Model loaded: $modelName")

            val interpreter = Interpreter(modelBuffer)
            val inputTensor = interpreter.getInputTensor(0)
            Log.d(TAG, "Model input tensor shape: ${inputTensor.shape().contentToString()}, dtype: ${inputTensor.dataType()}")

            val outputTensor = interpreter.getOutputTensor(0)
            val outputShape = outputTensor.shape()
            val outputDataType = outputTensor.dataType()
            Log.d(TAG, "Model output tensor shape: ${outputShape.contentToString()}, dtype: $outputDataType")

            val outputBuffer = Array(outputShape[0]) { FloatArray(outputShape[1]) }

            val startTime = SystemClock.uptimeMillis()
            interpreter.run(tensorImage.tensorBuffer.buffer, outputBuffer)
            val inferenceTime = SystemClock.uptimeMillis() - startTime
            Log.d(TAG, "Inference finished in $inferenceTime ms")

            interpreter.close()

            // Semua hasil diurutkan dari score tertinggi ke rendah
            val allResults = outputBuffer[0]
                .mapIndexed { index, score -> index to score }
                .sortedByDescending { it.second }

            // Filter yang lewat threshold dan ambil maxResults
            val topResults = allResults.filter { it.second >= threshold }
                .take(maxResults)

            val finalResults = if (topResults.isEmpty()) {
                Log.d(TAG, "No inference results above threshold $threshold, fallback to top results")
                allResults.take(maxResults)
            } else {
                topResults
            }

            finalResults.forEach {
                Log.d(TAG, "Index: ${it.first}, Score: ${"%.4f".format(it.second)}")
            }

            classifierListener?.onResults(finalResults, inferenceTime)

        } catch (e: Exception) {
            Log.e(TAG, "Exception during classifyImage: ${e.message}")
            classifierListener?.onError("Error running inference: ${e.message}")
        }
    }




    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(results: List<Pair<Int, Float>>, inferenceTime: Long)
    }
}
