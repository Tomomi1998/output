package kitamuralab;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
public class hands_right_output extends JPanel{


	double born_x[][] = new double[100][25]; //x座標を格納するための配列
	double born_y[][] = new double[100][25]; //y座標を格納するための配列
	//ディレクトリ指定
	File file =new File("video_json/2S_open1"
			+ "_result");
	File files[] = file.listFiles();
	ObjectMapper mapper = new ObjectMapper();
	int n=0;
	int count=0;
	Timer timer;

	public hands_right_output() {
		setOpaque(false);

		timer = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

//				Graphics g=getGraphics();		// Graphics を取り出して、
//			    g.setColor(getBackground());	// 描画色を背景色にして、
//			    g.dispose();
				repaint();
			}
		});
		timer.start();
	}

	double[] kakudo =new double[files.length];

	public void check() {
		while(n<files.length) {
			try{
				Thread.sleep(100);//0.1秒(100ミリ秒)間だけ処理を止める
			}catch(InterruptedException e) {
			}
		}
		
		
		for(int m=100; m>=40; m=m-10) {
			for(int l=0; l<files.length-2; l++) {
				
				correct(l+1,m);
				
				//System.out.println(this.kakudo[l]);
			}
		}
		
		
		double max=0;
		double min=180;
		
		for(int l=0; l<files.length; l++) {
			if(max<this.kakudo[l]) {
				max=this.kakudo[l];
			}
			
			if(min>this.kakudo[l]) {
				min=this.kakudo[l];
			}
		}
		
		
		for(int l=0; l<files.length; l++) {
			//System.out.println(this.kakudo[l]+","+max+","+min);
			//System.out.println(this.kakudo[l]);
		}
		
		output(max,min);
		
	}
	
	void correct(int k, double threshold) {
		
		double ave=(kakudo[k-1]+kakudo[k+1])/2;
		
		if(Math.abs(ave-kakudo[k])>threshold) {
			//System.out.println(k+","+this.kakudo[k]+","+ave);
			kakudo[k]=ave;
				
		}
	
		
	}
	
	void output(double max, double min) {
		
		//System.out.println(max+","+min);
		
		if(max-min<90) {
			System.out.println("空中姿勢で脇が開いています．");
		}
		else {
			System.out.println("空中姿勢の腕の締め方は正しいです．");
		}
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		if(n==files.length) {
			timer.stop();
			return;
		}

		try {
			JsonNode node = mapper.readTree(files[n]); //ファイルの読み込み
			//System.out.println("骨格座標の出力");
			double born_x[][]; //x座標を格納するための配列
			double born_y[][]; //y座標を格納するための配列
			born_x = new double[100][25];
			born_y = new double[100][25];

			int k = 0;

			while(node.get("people").get(k)!=null) {//k回
				int x = 0;
				int y = 0;
				double x0 = 0;
				double y0 = 0;

				for(int i=0; i<75; i+=3) {
					double point = node.get("people").get(k).get("pose_keypoints_2d").get(i).asDouble();
					born_x[k][x] = point; //配列に骨格のx座標を格納

					if(x0 < point) {
						x0=point;
					}
					if(x==24) {
						break;
					}
					else{
						x++;
					}
				}

				for(int j=1; j<75; j+=3) {
					double point = node.get("people").get(k).get("pose_keypoints_2d").get(j).asDouble();
					born_y[k][y] = point; //配列に骨格のy座標を格納

					if(y0 < point) {
						y0=point;
					}
					if(y==24) {
						break;
					}
					else{
						y++;
					}
				}

				double x1 = getWidth();
				double y1 = getHeight();
				double x2 = x0/x1;
				double y2 = y0/y1;
				double num_max = Math.max(x2,y2);
				double xy;
				if(num_max>1) {
					xy = num_max*1.5;
				}
				else {
					xy = 1;
				}



				//ここから座標を繋げて線での表示
				if(born_x[k][0]!=0 && born_x[k][1]!=0)
					{
					g2.setColor(Color.black);
					g2.draw(new Line2D.Double(born_x[k][0]/xy,born_y[k][0]/xy,born_x[k][1]/xy,born_y[k][1]/xy));
					}//首

				if(born_x[k][1]!=0 && born_x[k][2]!=0)
					{
					g2.setColor(Color.blue);
					g2.draw(new Line2D.Double(born_x[k][1]/xy,born_y[k][1]/xy,born_x[k][2]/xy,born_y[k][2]/xy));
					}//首から右肩
				if(born_x[k][2]!=0 && born_x[k][3]!=0)
					{
					g2.setColor(Color.green);
					g2.draw(new Line2D.Double(born_x[k][2]/xy,born_y[k][2]/xy,born_x[k][3]/xy,born_y[k][3]/xy));
					}//右肩から右肘
				if(born_x[k][3]!=0 && born_x[k][4]!=0)
					{
					g2.setColor(Color.orange);
					g2.draw(new Line2D.Double(born_x[k][3]/xy,born_y[k][3]/xy,born_x[k][4]/xy,born_y[k][4]/xy));
					}//右肘カラ右手首
				if(born_x[k][1]!=0 && born_x[k][5]!=0)
					{
					g2.setColor(Color.red);
					g2.draw(new Line2D.Double(born_x[k][1]/xy,born_y[k][1]/xy,born_x[k][5]/xy,born_y[k][5]/xy));
				}//首から左肩
				if(born_x[k][5]!=0 && born_x[k][6]!=0)
					{
					g2.setColor(Color.red);
					g2.draw(new Line2D.Double(born_x[k][5]/xy,born_y[k][5]/xy,born_x[k][6]/xy,born_y[k][6]/xy));
					}//左肩から左肘
				if(born_x[k][6]!=0 && born_x[k][7]!=0)
					{
					g2.setColor(Color.red);
					g2.draw(new Line2D.Double(born_x[k][6]/xy,born_y[k][6]/xy,born_x[k][7]/xy,born_y[k][7]/xy));
					}//左肘から左手首


				if(born_x[k][1]!=0 && born_x[k][8]!=0)
					{
					g2.setColor(Color.black);
					g2.draw(new Line2D.Double(born_x[k][1]/xy,born_y[k][1]/xy,born_x[k][8]/xy,born_y[k][8]/xy));
					}

				if(born_x[k][8]!=0 && born_x[k][9]!=0)
					{
					g2.setColor(Color.black);
					g2.draw(new Line2D.Double(born_x[k][8]/xy,born_y[k][8]/xy,born_x[k][9]/xy,born_y[k][9]/xy));
					}

				if(born_x[k][8]!=0 && born_x[k][12]!=0)
					{
					g2.setColor(Color.black);
					g2.draw(new Line2D.Double(born_x[k][8]/xy,born_y[k][8]/xy,born_x[k][12]/xy,born_y[k][12]/xy));
					}

				if(born_x[k][9]!=0 && born_x[k][10]!=0) g2.draw(new Line2D.Double(born_x[k][9]/xy,born_y[k][9]/xy,born_x[k][10]/xy,born_y[k][10]/xy));
				if(born_x[k][10]!=0 && born_x[k][11]!=0) g2.draw(new Line2D.Double(born_x[k][10]/xy,born_y[k][10]/xy,born_x[k][11]/xy,born_y[k][11]/xy));
				if(born_x[k][12]!=0 && born_x[k][13]!=0) g2.draw(new Line2D.Double(born_x[k][12]/xy,born_y[k][12]/xy,born_x[k][13]/xy,born_y[k][13]/xy));
				if(born_x[k][13]!=0 && born_x[k][14]!=0) g2.draw(new Line2D.Double(born_x[k][13]/xy,born_y[k][13]/xy,born_x[k][14]/xy,born_y[k][14]/xy));
				if(born_x[k][0]!=0 && born_x[k][15]!=0) g2.draw(new Line2D.Double(born_x[k][0]/xy,born_y[k][0]/xy,born_x[k][15]/xy,born_y[k][15]/xy));
				if(born_x[k][0]!=0 && born_x[k][16]!=0) g2.draw(new Line2D.Double(born_x[k][0]/xy,born_y[k][0]/xy,born_x[k][16]/xy,born_y[k][16]/xy));
				if(born_x[k][15]!=0 && born_x[k][17]!=0) g2.draw(new Line2D.Double(born_x[k][15]/xy,born_y[k][15]/xy,born_x[k][17]/xy,born_y[k][17]/xy));
				if(born_x[k][16]!=0 && born_x[k][18]!=0) g2.draw(new Line2D.Double(born_x[k][16]/xy,born_y[k][16]/xy,born_x[k][18]/xy,born_y[k][18]/xy));
				if(born_x[k][0]!=0 && born_x[k][15]!=0) g2.draw(new Line2D.Double(born_x[k][0]/xy,born_y[k][0]/xy,born_x[k][15]/xy,born_y[k][15]/xy));
				if(born_x[k][14]!=0 && born_x[k][19]!=0) g2.draw(new Line2D.Double(born_x[k][14]/xy,born_y[k][14]/xy,born_x[k][19]/xy,born_y[k][19]/xy));
				if(born_x[k][19]!=0 && born_x[k][20]!=0) g2.draw(new Line2D.Double(born_x[k][19]/xy,born_y[k][19]/xy,born_x[k][20]/xy,born_y[k][20]/xy));
				if(born_x[k][14]!=0 && born_x[k][21]!=0) g2.draw(new Line2D.Double(born_x[k][14]/xy,born_y[k][14]/xy,born_x[k][21]/xy,born_y[k][21]/xy));
				if(born_x[k][11]!=0 && born_x[k][23]!=0) g2.draw(new Line2D.Double(born_x[k][11]/xy,born_y[k][11]/xy,born_x[k][23]/xy,born_y[k][23]/xy));
				if(born_x[k][11]!=0 && born_x[k][22]!=0) g2.draw(new Line2D.Double(born_x[k][11]/xy,born_y[k][11]/xy,born_x[k][22]/xy,born_y[k][22]/xy));
				if(born_x[k][22]!=0 && born_x[k][24]!=0) g2.draw(new Line2D.Double(born_x[k][22]/xy,born_y[k][22]/xy,born_x[k][24]/xy,born_y[k][24]/xy));


				///////////////////////////////////////////////////////////////

				//k枚目のフレームの座標
				double neck_x = born_x[k][1];//首のx座標
				double neck_y = born_y[k][1];//首のy座標
				double rsholder_x = born_x[k][2];//右肩のx座標
				double rsholder_y = born_y[k][2];//右肩のy座標
				double relbow_x = born_x[k][3];//右肘のx座標
				double relbow_y = born_y[k][3];//右肘のy座標

				//ここから角度の計算
				double a1 = neck_x - rsholder_x;
				double a2 = neck_y - rsholder_y;
				double b1 = relbow_x - rsholder_x;
				double b2 = relbow_y - rsholder_y;

				double p = (a1 * b1) + (a2 * b2);//内積
				double pp =  Math.sqrt((a1*a1) + (a2*a2)) * Math.sqrt((b1*b1) + (b2*b2));

				double cos = p/pp;
				//System.out.println(cos); //cosθ
				double tt = Math.acos(cos);
				//System.out.println(tt); //ラジアン

				double kakudo = Math.toDegrees(tt);

				//System.out.println("kの出力");
				//System.out.println(kakudo); //右脇角度

				////////////////////////////////////////////////////////////////
				//フレームごとの角度を認識

				this.kakudo[n]=kakudo;

				//System.out.println(this.kakudo[n]);



				////////////////////////////////////////////////////////////////


				n++;
				k++;



			}





		}catch(IOException e) {
			e.printStackTrace(); //エラー処理
		}




	}


	public static void main(String[] args) {


		JFrame a = new JFrame();
		hands_right_output b =new hands_right_output();
		a.add(b);
		a.setSize(600,400);
		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		a.setVisible(true);
		
		b.check();


	}


}
