import org.frice.game.Game
import org.frice.game.obj.button.SimpleButton
import org.frice.game.obj.button.SimpleText
import org.frice.game.utils.graphics.shape.FRectangle

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.3
 */
class Demo10 extends Game {
	public static void main(String[] args) {
		new Demo10()
	}

	@Override
	protected void onInit() {
		addObject(new SimpleButton(new FRectangle(100, 100), "我是一个按钮", 50, 50, 100, 100))
		addObject(new SimpleText("啊啊啊啊", 40, 300, 300))
	}
}
