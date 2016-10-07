package demo;

import org.frice.game.Game;
import org.frice.game.obj.effects.FunctionEffect;
import org.frice.game.utils.time.FTimeListener;


public class Demo24 extends Game {
	private int count = 0;

	@Override
	protected void onInit() {
		addTimeListener(new FTimeListener(10, () -> {
			count++;
			clearObjects();
			addObject(new FunctionEffect(x -> (
					Math.sin((x + count) / 10) * 20 +
							Math.cos((x + count) / 20) * 20 +
							Math.sin((x + count) / 30) * 20 +
							Math.cos((x + count) / 40) * 20 +
							100
			), 0, 100, getWidth(), getHeight()));
			addObject(new FunctionEffect(x -> (
					Math.sin((x + count + count) / 30) * 20 +
							Math.cos((x + count + count) / 20) * 20 +
							Math.sin((x + count + count) / 40) * 20 +
							200
			), 0, 100, getWidth(), getHeight()));
		}));
	}

	public static void main(String[] args) {
		new Demo24();
	}
}
