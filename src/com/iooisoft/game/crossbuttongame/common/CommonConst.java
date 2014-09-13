package com.iooisoft.game.crossbuttongame.common;

public interface CommonConst {
    public static final int MARGIN=20;//边距  
    public static final int GRID_SPAN=21;//网格间距  
    public static final int ROWS=12;//棋盘行数  
    public static final int COLS=12;//棋盘列数  
    public static final int INIT_BUTTONS=20;//棋盘列数  
    public static final int MAX_POSITION_NUM = ROWS*COLS;
    public static final int MAX_BUTTON_NUM = 70;
    public static final int MAX_INTERVAL = 1200;
    public static final String IMAGE_FILE_PATH = System.getProperty("user.dir") + "\\img\\";
    public static final String RANK_FILE_PATH = System.getProperty("user.dir") + "\\data\\";
    public static final String GAME_TITLE = "穿纽扣游戏单机PC版";
    public static final String LABEL_SCORE = "得分:";
    public static final String LABEL_BUTTON = "纽扣:";
    public static final String LABEL_LEVEL = "级别:";
    public static final String RANK_LABEL_NAME = "姓名";
    public static final String RANK_LABEL_SCORE = "得分";
    public static final String RANK_LABEL_RANK = "排名";
    public static final String GAME_MENU = "游戏";
    public static final String GAME_OPTION_MENU = "游戏选项";
    public static final String ABOUT_MENU = "关于";
    public static final String VERSION_MENU_ITEM = "版本";
    public static final String START_GAME_ITEM = "开始游戏";
    public static final String PAUSE_GAME_ITEM = "暂停游戏";
    public static final String RESUME_GAME_ITEM = "继续游戏";
    public static final String STOP_GAME_ITEM = "停止游戏";
    public static final String EXIT_GAME_ITEM = "退出游戏";
    public static final String[] LEVEL_ITEMS = {"第一级","第二级","第三级","第四级","第五级","第六级"};
    public static final String RANK_ITEM = "排行榜";
    public static final String IS_EXIT_GAME = "是否结束游戏？";
    public static final String IS_RESTART_GAME = "是否重新开始游戏？";
    public static final String GAME_OVER = "游戏结束";
    public static final String SVAE_SCORE = "恭喜你获得了第%d名，请输入姓名。";
    public static final String ABOUT = "穿纽扣游戏单机PC版1.0 \n\nby 吴滔（wutao201408@163.com）";
    public static final int LEVEL_MAPPING_SCORE[] = { 2000,5000,8000,15000,30000};
}
