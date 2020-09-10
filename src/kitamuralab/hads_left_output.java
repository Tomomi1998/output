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
public class hads_left_output extends JPanel{
	double born_x[][] = new double[100][25]; //x座標を格納するための配列
	double born_y[][] = new double[100][25]; //y座標を格納するための配列
	//ディレクトリ指定
	File file =new File("video_json/hands_example_2_ok_result");
	File files[] = file.listFiles();
	ObjectMapper mapper = new ObjectMapper();
	int n=0;
	Timer timer;
	public hads_left_output() {
		setOpaque(false);
		timer = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				n++;
//				Graphics g=getGraphics();		// Graphics を取り出して、
//			    g.setColor(getBackground());	// 描画色を背景色にして、
//			    g.dispose();
				repaint();
			}
		});
		timer.start();
	}
	public void paintComponent(Graphics g) {
		
		int count=0;
		
		Graphics2D g2 = (Graphics2D)g;

		if(n==files.length-1) timer.stop();
		try {
			//JsonNode node = mapper.readTree(files[n]); //Jsonファイルの読み込み
			JsonNode node = mapper.readTree(files[n]); //ファイルの読み込み
			//System.out.println("骨格座標の出力");
			double born_x[][]; //x座標を格納するための配列
			double born_y[][]; //y座標を格納するための配列
			born_x = new double[100][25]; //
			born_y = new double[100][25];
			
			
			
			int k = 0;
			while(node.get("people").get(k)!=null) {
				int x = 0;
				int y = 0;
				double x0=0,y0=0;
				for(int i=0; i<75; i+=3) {
					double point = node.get("people").get(k).get("pose_keypoints_2d").get(i).asDouble();
					born_x[k][x] = point; //配列に骨格のx座標を格納
					
					//System.out.println();
					
					if(x0 < point) {
						x0=point;
					}
					if(x==24) {
						//System.out.println(born_x[k][x]);
						break;
					}
					else{
						//System.out.print(born_x[k][x]+",");
						x++;
					}
				}
				//System.out.println(x0);
				for(int j=1; j<75; j+=3) {
					double point = node.get("people").get(k).get("pose_keypoints_2d").get(j).asDouble();
					born_y[k][y] = point; //配列に骨格のy座標を格納
					if(y0 < point) {
						y0=point;
					}
					if(y==24) {
						//System.out.println(born_y[k][y]);
						break;
					}
					else{
						//System.out.print(born_y[k][y]+",");
						y++;
					}
				}
				//System.out.println(y0);
				//System.out.println();
				double x1 = getWidth();
				double y1 = getHeight();
				double x2 = x0/x1;
				double y2 = y0/y1;
				double num_max = Math.max(x2,y2);
				double xy;
				if(num_max>1) {
					xy = num_max*1.5;
					//System.out.println(xy);
				}else {
					xy = 1;
					//System.out.println(xy);
				}
				if(born_x[k][0]!=0 && born_x[k][1]!=0)
					{
					g2.setColor(Color.black);
					g2.draw(new Line2D.Double(born_x[k][0]/xy,born_y[k][0]/xy,born_x[k][1]/xy,born_y[k][1]/xy));
					}//首

				if(born_x[k][1]!=0 && born_x[k][2]!=0)
				{
				g2.setColor(Color.red);
				g2.draw(new Line2D.Double(born_x[k][1]/xy,born_y[k][1]/xy,born_x[k][2]/xy,born_y[k][2]/xy));
				}//首から右肩
				if(born_x[k][2]!=0 && born_x[k][3]!=0)
				{
				g2.setColor(Color.red);
				g2.draw(new Line2D.Double(born_x[k][2]/xy,born_y[k][2]/xy,born_x[k][3]/xy,born_y[k][3]/xy));
				}//右肩から右肘
				if(born_x[k][3]!=0 && born_x[k][4]!=0)
				{
				g2.setColor(Color.red);
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
				
				
				//System.out.println("角度の出力");
				double neck_x = born_x[k][1];
				double neck_y = born_y[k][1];
				double rsholder_x = born_x[k][5];
				double rsholder_y = born_y[k][5];
				double relbow_x = born_x[k][6];
				double relbow_y = born_y[k][6];
				
				double a1 = neck_x - rsholder_x;
				double a2 = neck_y - rsholder_y;
				double b1 = relbow_x - rsholder_x;
				double b2 = relbow_y - rsholder_y;
				
				double p = (a1 * b1) + (a2 * b2);
				double pp =  Math.sqrt((a1*a1) + (a2*a2)) * Math.sqrt((b1*b1) + (b2*b2));
				
				double cos = p/pp;
				
				//System.out.println(cos); //cosθ
				
				
				double tt = Math.acos(cos);
				//System.out.println(tt); //ラジアン
				
				double kakudo = Math.toDegrees(tt);
				System.out.println(kakudo); //左肩角度
				
				//ObjectMapper mapper = new ObjectMapper();
		        //String json = mapper.writeValueAsString(kakudo);
		       // System.out.println(json);
				
				if(kakudo<=120) {count++;}
				
				
				k++;
			}
			
			if(count<=10) {
				System.out.println("右脇が開いています");
			}
				
				
		}catch(IOException e) {
			e.printStackTrace(); //エラー処理
		}
		
		
		
	}
	
	
	public static void main(String[] args) {
		
		JFrame a = new JFrame();
		a.add(new hads_left_output());
		a.setSize(600,400);
		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		a.setVisible(true);
	}
	

}
