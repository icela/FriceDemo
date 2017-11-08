import org.frice.Game
import org.frice.obj.button.SimpleButton
import org.frice.obj.button.SimpleText
import static org.frice.Initializer.*

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.3
 */
class Demo10 extends Game {
	static void main(String[] args) {
		launch Demo10
	}

	@Override
	void onInit() {
		addObject new SimpleButton("我是一个按钮", 50, 50, 100, 100), new SimpleText("啊啊啊啊", 300, 300)
	}
}
