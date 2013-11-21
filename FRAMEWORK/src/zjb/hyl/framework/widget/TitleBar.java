package zjb.hyl.framework.widget;

public enum TitleBar {
	LEFT {
		@Override
		public int getInt() {
			// TODO Auto-generated method stub
			return -1;
		}
	},
	TEXT {
		@Override
		public int getInt() {
			// TODO Auto-generated method stub
			return 0;
		}
	},
	RIGHT {
		@Override
		public int getInt() {
			// TODO Auto-generated method stub
			return 1;
		}
	},
	TEXT2 {
		@Override
		public int getInt() {
			// TODO Auto-generated method stub
			return 2;
		}
	};
	public abstract int getInt();
}
