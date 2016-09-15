
import org.frice.game.utils.data.FileUtils
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.misc.forceRun
import org.frice.game.utils.web.HTMLUtils
import org.frice.game.utils.web.WebUtils
import java.util.*

/**
 * test: http://lofi.e-hentai.org/g/813858/63f5aba277/
 * test: http://www.yaoqmh.net/shaonvmanhua/5122.html
 * Created by ice1000 on 2016/9/3.

 * @author ice1000
 */
class Demo19 {
	fun process() {
		val scanner = Scanner(System.`in`)
		var count = 0
		while (scanner.hasNext()) forceRun {
			val images = HTMLUtils.findTag(WebUtils.readText(scanner.nextLine()), "img")
			images.forEachIndexed { index, i ->
				FLog.i("processing $i.... please wait.")
				val from = i.indexOf('\"') + 1
				val link = i.substring(from, i.indexOf('\"', from))
				FLog.i("url is $link")
				forceRun { FileUtils.image2File(WebUtils.readImage(link), "$count.png") }
				count++
			}
		}
	}
}

fun main(args: Array<String>) = Demo19().process()