package com.iooisoft.game.crossbuttongame.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameRank {
	
	public static int NO_RANK = -1;
	
	public class RankInfo {
		public RankInfo(String name,long score) {
			this.name = name;
			this.score = score;
		}
		private String name;
		private long score;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public long getScore() {
			return score;
		}
		public void setScore(long score) {
			this.score = score;
		}
		public String toString() {
			return "Name="+this.name+"\t\t\t"+"Score="+this.score;
		}
	}
	
	public class ComparatorRank implements Comparator<RankInfo> {
		public int compare(RankInfo rank1, RankInfo rank2) {
			if(rank1.score < rank2.score) {
				return 1;
			} else if( rank1.score > rank2.score ) {
				return -1;
			} else {
				return 0;
			}
		}
	}
	
	private List<RankInfo> rankList;
	private int maxRank;
	private String rankFilePath;
	private ComparatorRank comp;
	
	public GameRank(int maxRank,String rankFilePath) {
		this.rankList = new ArrayList<RankInfo>();
		this.maxRank = maxRank;
		this.rankFilePath = rankFilePath;
		this.comp = new ComparatorRank();
		readRankInfo(maxRank,rankFilePath);
	}
	
	public List<RankInfo> getRankInfo() {
		return this.rankList;
	}
	
	private void saveRankInfo() {
		File file =new File(rankFilePath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				return;
			}
		}
		FileWriter fileWritter = null;
		try {
			fileWritter = new FileWriter(rankFilePath);
	        for(RankInfo rank:this.rankList) {
	        	fileWritter.write(rank.toString()+System.getProperty("line.separator"));
	        	fileWritter.flush();
	        }
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if(fileWritter!=null) {
				try {
					fileWritter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int recordRank(String name,long score) {
		int rank = computeRank(score);
		if(rank<0) {
			return rank;
		}
		this.rankList.add(new RankInfo(name,score));
		sortRank();
		saveRankInfo();
		return rank;
	}
	
	private void readRankInfo(int maxRank,String rankFilePath) {
		
		Pattern pattern = Pattern.compile("^Name=([\\w 	]*)[\\t]+Score=(\\d+$)");
		BufferedReader reader = null;
		try {

			File file =new File(rankFilePath);
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					return;
				}
			}
			reader = new BufferedReader(new FileReader(file));
			String rankInfoLine = reader.readLine();
			while( rankInfoLine != null ) {
				rankInfoLine = rankInfoLine.trim();
				Matcher matcher = pattern.matcher(rankInfoLine);
				if(matcher.find()) {
					this.rankList.add(new RankInfo(matcher.group(1).trim(),Long.valueOf(matcher.group(2))));
				}
				rankInfoLine = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if( reader != null ) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		sortRank();
	}
	
	private void sortRank() {
		Collections.sort(this.rankList,this.comp);
		if( this.rankList.size()>this.maxRank ) {
			this.rankList = this.rankList.subList(0,this.maxRank);
		}
	}
	
	public int computeRank( long score ) {
		int cnt = this.rankList.size()-1;
		while(cnt>=0) {
			if( score <= this.rankList.get(cnt).getScore()) {
				break;
			}
			cnt--;
		}
		if( cnt < 0 ) {
			return 1;
		} else if( cnt+2 > this.maxRank ) {
			return NO_RANK;
		}else {
			return cnt+2;
		}
	}
}
