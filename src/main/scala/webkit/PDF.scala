package webkit

import java.io._

import io.github.cloudify.scala.spdf._

/**
  * Created by kasra.haghpanah on 31/03/2017.
  */
object PDF {

  def create(html: String): Array[Byte] = {


    // Create a new Pdf converter with a custom configuration
    // run `wkhtmltopdf --extended-help` for a full list of options
    val pdf = Pdf(
      /*new PdfConfig {
      orientation := Landscape
      pageSize := "Letter"
      marginTop := "1in"
      marginBottom := "1in"
      marginLeft := "1in"
      marginRight := "1in"
    }*/
      PdfConfig.default
    )




    // Save the PDF generated from the above HTML into a Byte Array
    val outputStream = new ByteArrayOutputStream
    pdf.run(html, outputStream)
    outputStream.toByteArray
    // Save the PDF of Google's homepage into a file
    //pdf.run(new URL("http://www.google.com"), new File("google.pdf"))
  }

}
