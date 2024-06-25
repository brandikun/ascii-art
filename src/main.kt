import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.floor

const val ASCII_WIDTH = 200

fun main() {
    val imageFile = File("images/cat.jpg")
    val image = ImageIO.read(imageFile)

    val scalingFactor = ASCII_WIDTH.toDouble() / image.width.toDouble()
    val asciiHeight = (image.height * scalingFactor).toInt()

    val resizedImage = resizeImage(image, ASCII_WIDTH, asciiHeight)

    val asciiArt = convertToASCII(resizedImage)

    println(asciiArt)
}

fun resizeImage(image: BufferedImage, width: Int, height: Int): BufferedImage {
    val resizedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val g = resizedImage.createGraphics()
    g.drawImage(image, 0, 0, width, height, null)
    g.dispose()
    return resizedImage
}

fun convertToASCII(image: BufferedImage): String {
    val sb = StringBuilder()

    for (y in 0..<image.height) {
        for (x in 0..<image.width) {
            val color = Color(image.getRGB(x, y))
            val intensity = listOf(color.red, color.green, color.blue).average()
            val asciiChar = intensityToASCII(intensity)
            sb.append(asciiChar)
        }
        sb.append("\n")
    }

    return sb.toString()
}

fun intensityToASCII(intensity: Double): Char {
    val asciiChars = " .:-=+*#%@"
    // tuned with floor to reduce brightness
    val normalizedIntensity = (floor(intensity * (asciiChars.length - 1) / 255)).toInt()

    return asciiChars[normalizedIntensity]
}
