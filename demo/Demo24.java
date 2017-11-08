package demo;

import org.frice.Game;
import org.frice.obj.effects.FunctionEffect;
import org.frice.utils.time.FTimeListener;

import static org.frice.Initializer.launch;


public class Demo24 extends Game {
	private int count = 0;

	@Override
	public void onInit() {
		addTimeListener(new FTimeListener(10, () -> {
			count++;
			clearObjects();
			addObject(
					new FunctionEffect(x -> (
							Math.sin((x + count) / 10) * 20 +
									Math.cos((x + count) / 20) * 20 +
									Math.sin((x + count) / 30) * 20 +
									Math.cos((x + count) / 40) * 20 +
									100
					), 0, 100, getWidth(), getHeight()),
					new FunctionEffect(x -> (
							Math.sin((x + count + count) / 30) * 20 +
									Math.cos((x + count + count) / 20) * 20 +
									Math.sin((x + count + count) / 40) * 20 +
									200
					), 0, 100, getWidth(), getHeight()));
		}));
	}

	public static void main(String[] args) {
		launch(Demo24.class);
	}
}
