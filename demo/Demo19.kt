import org.frice.platform.adapter.JvmImage
import org.frice.utils.*
import org.frice.utils.message.FLog
import java.util.*

/**
 * test: http://lofi.e-hentai.org/g/813858/63f5aba277/
 * test: http://www.yaoqmh.net/shaonvmanhua/5122.html
 * Created by ice1000 on 2016/9/3.
 *
 * @author ice1000
 */
fun process() {
	val scanner = Scanner(System.`in`)
	var count = 0
	while (scanner.hasNext()) forceRun {
		val images = findTag(readText(scanner.nextLine()), "img")
		images.forEach { i ->
			FLog.i("processing $i.... please wait.")
			val from = i.indexOf('\"') + 1
			val link = i.substring(from, i.indexOf('\"', from))
			FLog.i("url is $link")
			forceRun { (readImage(link) as JvmImage).image2File("$count.png") }
			count++
		}
	}
}

fun main(args: Array<String>) = process()