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
	
	public class Demo26 : Game
	{
		public override void OnInit()
		{
			Width = 800;
			Height = 600;

//			var b = new ShapeObject(ColorResource.小埋色, new FCircle(40), 300, 200);
			var a = new ShapeObject(ColorResource.吾王蓝, new FCircle(40), 300, 200);
//			var c = new ShapeObject(ColorResource.基佬紫, new FCircle(40), 300, 200);
			//replace with a file path in disk
//			var b = ImageObject.FromFile(@"C:\frice.png", 300, 200, 100, 100);
			var b = ImageObject.FromWeb("https://avatars3.githubusercontent.com/u/16398479", 300, 200, 100, 100);
			var c = ImageObject.FromWeb("https://avatars1.githubusercontent.com/u/21008243", 300, 200, 100, 100);
//			AddObjects(a, b, c);
			AddObject(a);
			AddObject(b);
			AddObject(c);
			RandomMove(a, 1000);
			RandomMove(b, 1500);
			RandomMove(c, 750);
		}

		public void RandomMove(FObject obj, int time)
		{
			var ft2 = new FTimeListener2(time, true);
			ft2.OnTimeUp += () =>
			{
				obj.MoveList.Clear();
				obj.MoveList.Add(GetRandomMove());
			};
			ft2.Start();
		}

		private static SimpleMove GetRandomMove()
		{
			var r = new Random();
			var x = r.Next(-100, 100);
			var r2 = new Random(x);
			var y = r.Next(-100, 100);

			return new SimpleMove(x, y);
		}
		
		public static void Main(string[] args)
		{
			Application.Run(new Demo26());
		}
	}
}