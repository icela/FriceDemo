import org.frice.Game;
import org.frice.event.OnMouseEvent;
import org.frice.obj.sub.ImageObject;
import org.frice.resource.image.FileImageResource;
import org.frice.resource.image.FrameImageResource;
import org.frice.utils.audio.AudioManager;
import org.frice.utils.audio.AudioPlayer;
import org.jetbrains.annotations.NotNull;

import static org.frice.Initializer.launch;

/**
 * Created by ice1000 on 2016/8/16.
 *
 * @author ice1000
 * @since v0.3.1
 */
public class Demo8 extends Game {

	private AudioPlayer player;
	private int cnt = 0;

	@Override
	public void onInit() {
		ImageObject object = new ImageObject(new FrameImageResource(this, new FileImageResource[]{
				new FileImageResource("1.png"),
				new FileImageResource("2.png"),
				new FileImageResource("3.png"),
				new FileImageResource("4.png"),
				new FileImageResource("5.png")
		}, 1000), 100.0, 100.0);
		addObject(object);
		setCursor(object);
		player = AudioManager.getPlayer("1.wav");
		player.start();
//		new Color(0x1BA61C);
	}

	@Override
	public void onMouse(@NotNull OnMouseEvent e) {
		cnt++;
		if (cnt > 10) player.exit();
	}

	public static void main(String[] args) {
		launch(Demo8.class);
	}
}
