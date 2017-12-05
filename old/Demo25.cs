using System;
using System.Windows.Forms;
using FriceEngine;
using FriceEngine.Animation;
using FriceEngine.Object;
using FriceEngine.Resource;
using FriceEngine.Utils.Graphics;
using FriceEngine.Utils.Message;
using FriceEngine.Utils.Time;

namespace Frice.Demo
{
	public class Demo25 : Game
	{
		public override void OnInit()
		{
			SetBounds(300, 300, 800, 600);

			SetTitle("Fuck the world");

			//replace with a file path in desk
			var b = ImageObject.FromFile(@"C:\frice.png", 300, 400, 50, 50);
//			var c = ImageObject.FromWeb("https://avatars1.githubusercontent.com/u/21008243", 400, 300);

			//can resize：
//			c.Height = 100;
//			c.Width = 100;
			b.MoveList.Add(new SimpleMove(-10, -10));
//			c.MoveList.Add(new SimpleMove(-10, 10));
			AddObject(b);
			AddObject(new SimpleText(ColorResource.高坂穗乃果, "Hello World", 10, 10));
//			AddObject(c);
		}

		private void Add()
		{
			var a = ImageObject.FromFile(@"C:\frice.png", Mouse.X - 50, Mouse.Y - 50, 100, 100);
			a.MoveList.Add(new SimpleMove(100, -400));
			a.MoveList.Add(new AccelerateMove(0, 1000));
			AddObject(a);
		}

		public override void OnClick(FPoint mousePosition)
		{
			FLog.D("On click called.");
			Add();
			base.OnClick(mousePosition);
		}

		public override void OnRefresh()
		{
			Add();
			base.OnRefresh();
		}
		
		public static void Main(string[] args)
		{
			Application.Run(new Demo25());
		}

	}
}