package com.pa.sugarcare.utility

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import kotlin.math.exp


class ImageClassifierHelper(private val context: Context) {

    private var interpreter: Interpreter? = null
    private var labels: List<String> = emptyList()
    private val modelPath = "my_model_quantized_uint8.tflite"
    private val labelPath = "labels.txt"
    private val imageSizeX = 224
    private val imageSizeY = 224
    private val imageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.BILINEAR))
        .build()

    init {
        try {
            // Load the TFLite model and labels
            val options = Interpreter.Options()
            interpreter = Interpreter(loadModelFile(), options)
            println("Model TFLite berhasil dimuat.")

            labels = loadLabelList()
            println("Label berhasil dimuat: ${labels.size} label.")

        } catch (e: Exception) {
            println("Error saat menginisialisasi classifier: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun loadLabelList(): List<String> {
        return context.assets.open(labelPath).bufferedReader().useLines { it.toList() }
    }

    private fun isReady(): Boolean = interpreter != null && labels.isNotEmpty()

    @SuppressLint("DefaultLocale")
    fun classifyImage(bitmap: Bitmap): String {
        if (!isReady()) {
            return "Model belum siap. Pastikan model dan label sudah dimuat."
        }

        try {
            // Pra-pemrosesan gambar
            val tensorImage = TensorImage(DataType.UINT8).apply {
                load(bitmap)
            }
            val processedImage = imageProcessor.process(tensorImage)

            // Siapkan buffer output
            val outputBuffer = TensorBuffer.createFixedSize(
                intArrayOf(1, labels.size),
                DataType.UINT8
            )

            // Jalankan inferensi
            interpreter?.run(processedImage.buffer, outputBuffer.buffer)

            // Dapatkan detail output tensor
            val outputTensor = interpreter!!.getOutputTensor(0)
            val outputScale = outputTensor.quantizationParams().scale
            val outputZeroPoint = outputTensor.quantizationParams().zeroPoint

            Log.d(TAG, "Output Scale: $outputScale")
            Log.d(TAG, "Output Zero Point: $outputZeroPoint")

            // --- De-kuantisasi output ---
            val outputByteBuffer = outputBuffer.buffer
            outputByteBuffer.rewind()

            val numClasses = labels.size
            val outputArrayFloat = FloatArray(numClasses)

            for (i in 0 until numClasses) {
                val byteValue = outputByteBuffer.get().toInt() and 0xFF
                outputArrayFloat[i] = ((byteValue - outputZeroPoint) * outputScale)
            }

            // Cetak logit mentah
            Log.d(TAG, "Android - Prediksi Logit (setelah de-kuantisasi): ${outputArrayFloat.take(10).joinToString(", ")}...")
            Log.d(TAG, "Android - Shape Logit: [${outputArrayFloat.size}]")

            // --- Terapkan Softmax Manual ---
            val probs = stableSoftmax(outputArrayFloat)

            // Cetak probabilitas setelah softmax
            Log.d(TAG, "Android - Probabilitas (setelah Softmax): ${probs.take(10).joinToString(", ")}...")
            Log.d(TAG, "Android - Shape Probabilitas: [${probs.size}]")
            Log.d(TAG, "Android - Sum Probabilitas: ${probs.sum()}") // Harus mendekati 1.0

            // Cari kelas dengan probabilitas tertinggi
            val maxIndex = probs.indices.maxByOrNull { probs[it] } ?: 0
            val confidence = probs[maxIndex] * 100f

            // Mapping hasil ke label
            return if (maxIndex < labels.size) {
                "${labels[maxIndex]} (${String.format("%.2f", confidence)}%)"
            } else {
                Log.w(TAG, "maxIndex $maxIndex out of bounds for label size ${labels.size}")
                "Unknown object"
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error during inference", e)
            return "Error: ${e.message}"
        }
    }

    private fun stableSoftmax(logits: FloatArray): FloatArray {
        val max = logits.maxOrNull() ?: 0f
        var expSum = 0.0f

        val exps = logits.map {
            val e = exp((it - max).toDouble()).toFloat()
            expSum += e
            e
        }.toFloatArray()

        for (i in exps.indices) {
            exps[i] = exps[i] / expSum
        }

        return exps
    }

    companion object {
        const val TAG = "TensorFlowLiteHelper"
    }
}