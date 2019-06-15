import java.io.*;
import java.util.*;
class PayoutProblem{
	public static void main(String[] args) throws FileNotFoundException, IOException{
		solve();	
	}

	static void solve() throws FileNotFoundException, IOException{
		String in = "D:\\Selise\\input.txt";
		String group = "D:\\Selise\\group.txt";
		String semi = "D:\\Selise\\semi.txt";
		String fin = "D:\\Selise\\fin.txt";
		String out = "D:\\Selise\\output.txt";
		BufferedReader br = new BufferedReader(new FileReader(in));
		BufferedWriter bw = new BufferedWriter(new FileWriter(out));
		StringTokenizer st = null;
		int count = 0;
		// Team[] array = new Team[100];
		TreeMap map = new TreeMap();
		TreeMap mapMoney = new TreeMap();
		String str = br.readLine();
		while(str != null && str.length() > 0){
			st = new StringTokenizer(str,",");
			String name = st.nextToken();
			double price = Double.parseDouble(st.nextToken());
			// array[count] = new Team(name, price);
			Team t = new Team(name, price);
			map.put(name, t);
			mapMoney.put(name, t.common());
			count++;
			// bw.write(name+"  "+price);
			str = br.readLine();
		}
		bw.write("AFTER GROUP MATCHES : ");
		bw.newLine();
		bw.newLine();
		br = new BufferedReader(new FileReader(group));
		str = br.readLine();
		while(str != null && str.length() > 0){
			st = new StringTokenizer(str,",");
			st.nextToken();
			Team t1 = null;
			Team t2 = null;
			Team tw = null;
			String strr = st.nextToken();
			// bw.write(strr);
			t1 = (Team)map.get(strr);
			String str1 = st.nextToken();
			t2 = (Team)map.get(str1);
			String str2 = st.nextToken();
			tw = (Team)map.get(str2);
			str = br.readLine();
			Team tlose = null;
			if (str2.equals(strr))
				tlose = (Team) map.get(str1);
			else
				tlose = (Team) map.get(strr);
			double money = (double)mapMoney.get(str2) + tw.get_match_earn(tlose,count);
			mapMoney.put(str2, money);
		}
		Set eSet = mapMoney.entrySet();
		Iterator it = eSet.iterator();
		while (it.hasNext()){
			Map.Entry me = (Map.Entry)it.next();
			bw.write(me.getKey() + " : "+ me.getValue());
			bw.newLine();
		}
		bw.newLine();
		bw.write("AFTER SEMI_FINAL MATCHES : ");
		bw.newLine();
		bw.newLine();
		br = new BufferedReader(new FileReader(semi));
		str = br.readLine();
		String[] semiString = new String[4];
		int countSemi = 0;
		String[] semiWinString = new String[2];
		int countSemiWin = 0;
		while(str != null && str.length() > 0){
			st = new StringTokenizer(str,",");
			// st.nextToken();
			Team t1 = null;
			Team t2 = null;
			Team tw = null;
			String strr = st.nextToken();
			t1 = (Team)map.get(strr);
			semiString[countSemi] = strr;
			countSemi++;
			String str1 = st.nextToken();
			t2 = (Team)map.get(str1);
			semiString[countSemi] = str1;
			countSemi++;
			String str2 = st.nextToken();
			tw = (Team)map.get(str2);
			semiWinString[countSemiWin] = str2;
			countSemiWin++;
			str = br.readLine();
		}
		String[] semilooseString = new String[2];
		int countSemiLoose = 0;
		Set set = map.entrySet();
		Iterator i = set.iterator();
		while (i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			if(!me.getKey().equals(semiString[0]) && !me.getKey().equals(semiString[1]) && !me.getKey().equals(semiString[2]) && !me.getKey().equals(semiString[3])){
				Team tt = (Team) me.getValue();
				// tt.disqualify();
			}
			else{
				if (!me.getKey().equals(semiWinString[0]) && !me.getKey().equals(semiWinString[1])){
					semilooseString[countSemiLoose] = (String)me.getKey();
					countSemiLoose++;
				}
			}
		}
		mapMoney.put(semiWinString[0], (double)mapMoney.get(semiWinString[0])+ ((Team)map.get(semiWinString[0])).semifinal((Team)map.get(semilooseString[0]), map, semiString));
		bw.write(semiWinString[0]+ " : " + mapMoney.get(semiWinString[0]));
		bw.newLine();
		mapMoney.put(semiWinString[1], (double)mapMoney.get(semiWinString[1])+ ((Team)map.get(semiWinString[1])).semifinal((Team)map.get(semilooseString[1]), map, semiString));
		bw.write(semiWinString[1]+ " : " + mapMoney.get(semiWinString[1]));
		bw.newLine();
		br = new BufferedReader(new FileReader(fin));
		str = br.readLine();
		bw.newLine();
		bw.write("AFTER FINAL MATCHES : ");
		bw.newLine();
		while(str != null && str.length() > 0){
			st = new StringTokenizer(str,",");
			// st.nextToken();
			Team t1 = null;
			Team t2 = null;
			Team tw = null;
			String strr = st.nextToken();
			t1 = (Team)map.get(strr);
			String str1 = st.nextToken();
			t2 = (Team)map.get(str1);
			String str2 = st.nextToken();
			tw = (Team)map.get(str2);
			str = br.readLine();
			mapMoney.put(str2, 
							(double)mapMoney.get(str2) + tw.winer(((str2.equals(strr) ? t2 : t1)))
						);
			mapMoney.put((str2.equals(strr) ? str1 : strr), (double)mapMoney.get(str2.equals(strr) ? str1 : strr)+ ((Team)map.get(str2.equals(strr) ? str1 : strr)).runnerup());
			bw.write(str2+ " : "+ mapMoney.get(str2));
			bw.newLine();
			bw.write((str2.equals(strr) ? str1 : strr)+ " : "+ mapMoney.get((str2.equals(strr) ? str1 : strr)));
		}
		bw.close();

	}

}
class Team{
	private String mName;
	private double mPrice;
	private double mSum = 0;
	private double match_fee = 0;
	private double win_draw_fee = 0;
	Team(String name, double price){
		mName = name;
		mPrice = price;
	}
	String getName(){
		return mName;
	}
	public double common()
    {
        double service_charge;
        

        service_charge = mPrice * .1;
        mPrice -= service_charge;

        match_fee = mPrice * .1;
        mPrice -= match_fee;
        return match_fee;
    }


    public double get_match_earn(Team t,int n)
    {

        double mp = 0; 
        double bonus = 0;
        mp = get_amount_of_onematch(n);
        bonus = t.get_amount_of_onematch(n);
        return mp + bonus;
    }

    private double get_amount_of_onematch(int n)
    {	if (win_draw_fee == 0){
	        int no_of_match = (n/2) - 1;
	        double tmp = 0.0;
	        win_draw_fee = mPrice*.5;
	        win_draw_fee = win_draw_fee/no_of_match;
	    }
        mPrice -= win_draw_fee;
        return win_draw_fee;
    }

    public double disqualify(double sum)
    {
        sum += mPrice;
        return sum;
    }

    public double semifinal(Team t, TreeMap map, String[] semiString)
    {
    	if (mSum == 0.0){
	    	Set set = map.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext()){
				Map.Entry me = (Map.Entry)i.next();
				if(!me.getKey().equals(semiString[0]) && !me.getKey().equals(semiString[1]) && !me.getKey().equals(semiString[2]) && !me.getKey().equals(semiString[3])){
					Team tt = (Team) me.getValue();
					mSum = tt.disqualify(mSum);
				}
			}
		}
        double pay, pay1, pay2;
        pay = mSum * 0.125;
       
        return pay + semiOneThird() + t.semiOneThird();
    }
    double semiOneThird(){
    	double tmp = mPrice / 3;
    	mPrice = mPrice * 2 / 3;
    	return tmp;
    }
    public double winer(Team t){
    	return mPrice + t.mPrice + mSum / 2;
    }
    public double runnerup(){
    	return mSum / 4;
    }
    
}