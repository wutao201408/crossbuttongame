
package com.iooisoft.game.crossbuttongame.gui;  

import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

import com.iooisoft.game.crossbuttongame.common.CommonConst;
import com.iooisoft.game.crossbuttongame.game.GameEventListener;
import com.iooisoft.game.crossbuttongame.game.GameIF;
import com.iooisoft.game.crossbuttongame.game.GameRank;
import com.iooisoft.game.crossbuttongame.game.GameRank.RankInfo;
import com.iooisoft.game.crossbuttongame.game.GameStatus;
import com.iooisoft.game.crossbuttongame.game.position.state.PositionState.StateValue;

public class GameFrame extends JFrame {  

	private static final long serialVersionUID = 1L;
	private BoardPanel boardPanel;
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenu gameOptionMenu;
	private JMenu aboutMenu;
	private GameIF game;
	private JMenuItem startGameItem,pauseGameItem,resumeGameItem,
		stopGameItem,exitGameItem,aboutItem;
	private JRadioButtonMenuItem[] levelItems;
	private JMenuItem rankItem;
	private JLabel scoreLabel,btnNumLabel,levelLabel;
	private JLabel scoreValueLabel,btnNumValueLabel,levelValueLabel;
	
	class GameEventReciver implements GameEventListener {
		public void gameDataUpdate(Object parameter) {
			final GameStatus gameStatus = (GameStatus)parameter;
			boardPanel.setBoardStates(gameStatus.getPosState());
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					scoreValueLabel.setText(String.valueOf(gameStatus.getScore()));
					btnNumValueLabel.setText(String.valueOf(gameStatus.getRemainButtons()));
					levelValueLabel.setText(String.valueOf(gameStatus.getLevel()));
					GameFrame.this.repaint();
				}
			});
		}
		
		public void changeLevel(Object parameter) {
			final GameStatus gameStatus = (GameStatus)parameter;
			boardPanel.setBoardStates(gameStatus.getPosState());
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					levelValueLabel.setText(String.valueOf(gameStatus.getLevel()));
				}
			});
		}
		
		public void gameOver(final Object parameter) {
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					GameStatus gameStatus = (GameStatus)parameter;
					if( gameStatus.getRank() < 0 ) {
						JOptionPane.showMessageDialog(GameFrame.this, CommonConst.GAME_OVER, CommonConst.GAME_TITLE, JOptionPane.INFORMATION_MESSAGE);
					} else {
						String saveRecord = String.format(CommonConst.SVAE_SCORE,gameStatus.getRank());
						String name = JOptionPane.showInputDialog(GameFrame.this, saveRecord, CommonConst.GAME_TITLE, JOptionPane.INFORMATION_MESSAGE);
						if( name != null ) {
							int rank = game.recordRank(name, gameStatus.getScore());
			        		JOptionPane.showMessageDialog(GameFrame.this,  buildRankInfo(rank), 
				        			CommonConst.GAME_TITLE, JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
		}
	}
	
	private String buildRankInfo(int highlightRank) {
    	StringBuilder rankInfo = new StringBuilder();
    	rankInfo.append("<html><table>");
		rankInfo.append("<tr><td>");
		rankInfo.append(CommonConst.RANK_LABEL_RANK);
		rankInfo.append("</td><td>");
		rankInfo.append(CommonConst.RANK_LABEL_NAME);
		rankInfo.append("</td><td>");
		rankInfo.append(CommonConst.RANK_LABEL_SCORE);
		rankInfo.append("</td></tr>");
		rankInfo.append("<tr><td>");
		rankInfo.append("------");
		rankInfo.append("</td><td>");
		rankInfo.append("------");
		rankInfo.append("</td><td>");
		rankInfo.append("------");
		rankInfo.append("</td></tr>");

    	List<RankInfo> list = game.getRank();
    	for( int cnt=0;cnt<list.size();cnt++ ) {
    		rankInfo.append("<tr><td>");
    		if(cnt==highlightRank-1) {
    			rankInfo.append("<font color=\"red\">");
    		}
    		rankInfo.append(cnt+1);
    		if(cnt==highlightRank-1) {
    			rankInfo.append("</font>");
    		}
    		rankInfo.append("</td><td>");
    		if(cnt==highlightRank-1) {
    			rankInfo.append("<font color=\"red\">");
    		}
    		rankInfo.append(list.get(cnt).getName());
    		if(cnt==highlightRank-1) {
    			rankInfo.append("</font>");
    		}
    		rankInfo.append("</td><td>");
    		if(cnt==highlightRank-1) {
    			rankInfo.append("<font color=\"red\">");
    		}
    		rankInfo.append(list.get(cnt).getScore());
    		if(cnt==highlightRank-1) {
    			rankInfo.append("</font>");
    		}
    		rankInfo.append("</td></tr>");
    		if(cnt==highlightRank-1) {
    			rankInfo.append("</font>");
    		}
    	}
    	rankInfo.append("</table></html>");
    	return rankInfo.toString();
	}
	
	class GameMenuAction implements ActionListener,GameEventListener {
			
		public void actionPerformed(ActionEvent e){  
			Object obj=e.getSource();
	        if(obj==startGameItem){
	        	GameStatus status = game.getGameStatus();
	        	if( status.getGameState() != GameIF.GameState.STOP ) {
	        		game.pauseGame();
	        		int ret = JOptionPane.showConfirmDialog(GameFrame.this, CommonConst.IS_RESTART_GAME, CommonConst.GAME_TITLE, JOptionPane.YES_NO_OPTION);
		        	if( ret != JOptionPane.OK_OPTION ) {
		        		game.resumeGame();
		        		return;
		        	}
	        	}
	        	stopGameItem.setEnabled(true);;
	        	pauseGameItem.setEnabled(true);
	        	pauseGameItem.setVisible(true);
	        	resumeGameItem.setVisible(false);
	        	for( int cnt=0;cnt<levelItems.length;cnt++ ) {
	        		levelItems[cnt].setEnabled(false);
	        		if( levelItems[cnt].isSelected() == true ) {
	        			game.setGameLevel(cnt+1);
	        		}
	        	}
	        	game.startGame();
	        }  
	        else if (obj==stopGameItem)  {
	        	game.pauseGame();
	        	int ret = JOptionPane.showConfirmDialog(GameFrame.this, CommonConst.IS_EXIT_GAME, CommonConst.GAME_TITLE, JOptionPane.YES_NO_OPTION);
	        	if( ret != JOptionPane.OK_OPTION ) {
	        		game.resumeGame();
	        		return;
	        	}
	        	stopGameItem.setEnabled(false);
	        	pauseGameItem.setEnabled(false);
	        	for( JRadioButtonMenuItem item: levelItems) {
	        		item.setEnabled(true);
	        	}
	        	game.stopGame();
	        }
	        else if (obj==pauseGameItem){
	        	pauseGameItem.setVisible(false);
	        	resumeGameItem.setVisible(true);
	        	stopGameItem.setEnabled(false);
	        	game.pauseGame(); 
	        } 
	        else if( obj == resumeGameItem ) {
	        	resumeGameItem.setVisible(false);
	        	pauseGameItem.setVisible(true);
	        	stopGameItem.setEnabled(true);
	        	game.resumeGame();
	        }
	        else if( obj==exitGameItem ) {
	        	System.exit(0);
	        }
	        else if( obj==aboutItem ) {
	        	GameStatus status = game.getGameStatus();
	        	if( status.getGameState() == GameIF.GameState.RUNNING ) {
	        		game.pauseGame();
	        		JOptionPane.showMessageDialog(GameFrame.this, CommonConst.ABOUT, 
		        			CommonConst.GAME_TITLE, JOptionPane.INFORMATION_MESSAGE);
		        	game.resumeGame();
	        	} else {
	        		JOptionPane.showMessageDialog(GameFrame.this, CommonConst.ABOUT, 
		        			CommonConst.GAME_TITLE, JOptionPane.INFORMATION_MESSAGE);
	        	}
	        } else if( obj==rankItem ) {
	        	
	        	GameStatus status = game.getGameStatus();
	        	if( status.getGameState() == GameIF.GameState.RUNNING ) {
	        		game.pauseGame();
	        		JOptionPane.showMessageDialog(GameFrame.this, buildRankInfo(GameRank.NO_RANK), 
		        			CommonConst.GAME_TITLE, JOptionPane.INFORMATION_MESSAGE);
		        	game.resumeGame();
	        	} else {
	        		JOptionPane.showMessageDialog(GameFrame.this,  buildRankInfo(GameRank.NO_RANK), 
		        			CommonConst.GAME_TITLE, JOptionPane.INFORMATION_MESSAGE);
	        	}
	        }
		}
				
		public void gameDataUpdate(Object parameter) {
		}

		public void gameOver(Object parameter) {
        	stopGameItem.setEnabled(false);
        	pauseGameItem.setEnabled(false);
        	for( JRadioButtonMenuItem item: levelItems) {
        		item.setEnabled(true);
        	}
        	game.stopGame();
		}

		public void changeLevel(Object parameter) {

		}
	}

	class LeveItemAction implements ItemListener {
		public void itemStateChanged(ItemEvent event) {
			JRadioButtonMenuItem target =
		    	        (JRadioButtonMenuItem)event.getSource();
			String level = target.getActionCommand();
			levelValueLabel.setText(level);
			game.setGameLevel(Integer.parseInt(level));
		}
	}
	
	public GameFrame(GameIF game){  
		
		this.game = game;
		this.boardPanel = new BoardPanel(game);
		
		this.scoreLabel = new JLabel(CommonConst.LABEL_SCORE);
        this.scoreLabel.setBounds(new Rectangle(300,20,100,30));
        this.add(scoreLabel);
        this.scoreLabel.setFont(new Font("Monospaced",1,20));
        
        this.scoreValueLabel = new JLabel("0");
        this.scoreValueLabel.setBounds(new Rectangle(380,20,100,30));
        this.add(scoreValueLabel);
        this.scoreValueLabel.setFont(new Font("Monospaced",1,20));
        
        this.btnNumLabel = new JLabel(CommonConst.LABEL_BUTTON);
        this.btnNumLabel.setBounds(new Rectangle(300,100,100,30));
        this.add(btnNumLabel);
        this.btnNumLabel.setFont(new Font("Monospaced",1,20));
        
        this.btnNumValueLabel = new JLabel("70");
        this.btnNumValueLabel.setBounds(new Rectangle(380,100,100,30));
        this.add(btnNumValueLabel);
        this.btnNumValueLabel.setFont(new Font("Monospaced",1,20));
        
        this.levelLabel = new JLabel(CommonConst.LABEL_LEVEL);
        this.levelLabel.setBounds(new Rectangle(300,180,100,30));
        this.add(levelLabel);
        this.levelLabel.setFont(new Font("Monospaced",1,20));
        
        this.levelValueLabel = new JLabel("1");
        this.levelValueLabel.setBounds(new Rectangle(380,180,100,30));
        this.add(levelValueLabel);
        this.levelValueLabel.setFont(new Font("Monospaced",1,20));
		
		//设置标题
		this.setTitle(CommonConst.GAME_TITLE);
		Image img = new ImageIcon(CommonConst.IMAGE_FILE_PATH+StateValue.CL_BTN_NOLINE+".png").getImage();
		this.setIconImage(img);
		
        //创建菜单栏和第一级菜单  
        menuBar =new JMenuBar();//初始化菜单栏  
        GameMenuAction gameMenuAction = new GameMenuAction();
        this.gameMenu=new JMenu(CommonConst.GAME_MENU);//初始化菜单
        this.gameOptionMenu = new JMenu(CommonConst.GAME_OPTION_MENU);
        this.aboutMenu = new JMenu(CommonConst.ABOUT_MENU);
        
        //初始化[游戏]菜单的各个项目  
        this.startGameItem=new JMenuItem(CommonConst.START_GAME_ITEM);
        this.pauseGameItem=new JMenuItem(CommonConst.PAUSE_GAME_ITEM);
        this.resumeGameItem=new JMenuItem(CommonConst.RESUME_GAME_ITEM);
        this.stopGameItem=new JMenuItem(CommonConst.STOP_GAME_ITEM);
        this.exitGameItem=new JMenuItem(CommonConst.EXIT_GAME_ITEM);
        this.gameMenu.add(this.startGameItem);  
        this.gameMenu.add(this.pauseGameItem);
        this.gameMenu.add(this.resumeGameItem);
        this.gameMenu.add(this.stopGameItem);  
        this.gameMenu.addSeparator();
        this.gameMenu.add(this.exitGameItem);  
        this.startGameItem.addActionListener(gameMenuAction);  
        this.pauseGameItem.addActionListener(gameMenuAction);  
        this.stopGameItem.addActionListener(gameMenuAction);
        this.exitGameItem.addActionListener(gameMenuAction);
        this.resumeGameItem.addActionListener(gameMenuAction);
        this.game.addGameEventListener(gameMenuAction);
        this.pauseGameItem.setEnabled(false);
        this.stopGameItem.setEnabled(false);
        this.resumeGameItem.setVisible(false);
        
        // 初始化[游戏选项]菜单的各个项目
        this.levelItems = new JRadioButtonMenuItem[CommonConst.LEVEL_ITEMS.length];
        ButtonGroup bg = new ButtonGroup();
        LeveItemAction levelAct = new LeveItemAction();
        for(int cnt=0;cnt<this.levelItems.length;cnt++) {
        	this.levelItems[cnt] = new JRadioButtonMenuItem(CommonConst.LEVEL_ITEMS[cnt]);
        	this.levelItems[cnt].setActionCommand(String.valueOf(cnt+1));
        	this.levelItems[cnt].addItemListener(levelAct);
        	bg.add(this.levelItems[cnt]);
        	this.gameOptionMenu.add(this.levelItems[cnt]);
        }
        levelItems[0].setSelected(true);
        this.rankItem = new JMenuItem(CommonConst.RANK_ITEM); 
        this.gameOptionMenu.addSeparator();
        this.gameOptionMenu.add(rankItem);
        this.rankItem.addActionListener(gameMenuAction);
        
        // 初始化[关于]菜单的各个项目
        this.aboutItem = new JMenuItem(CommonConst.VERSION_MENU_ITEM);
        this.aboutMenu.add(this.aboutItem);
        this.aboutItem.addActionListener(gameMenuAction);
        
        GameEventReciver eventReciver = new GameEventReciver();
        this.game.addGameEventListener(eventReciver);

        menuBar.add(this.gameMenu); 
        menuBar.add(this.gameOptionMenu);
        menuBar.add(this.aboutMenu);
        
        //将menuBar设置为菜单栏
        setJMenuBar(menuBar);  
        
        //将棋盘界面对象添加到窗体上  
        add(this.boardPanel);
        //设置界面关闭事件  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setResizable(false);
        setSize(500,350);  
	}  
}  