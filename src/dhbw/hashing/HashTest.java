package dhbw.hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class HashTest {
	
	private static int limit = 500;
	private static int items = 0;
	private static HashMap<String, Bucket> map = new LinkedHashMap<String, Bucket>();
	private static int collisions = 0;
	private static String	col50 = "",
							col90 = "",
							col95 = "",
							col100 = "";
	private static HashMethod currentMethod = null; 
	
	private static String MD5(String s) {
		StringBuffer hex = new StringBuffer();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(s.getBytes());
			byte[] res = md5.digest();
			for(int i = 0; i < res.length; i++) {
				if(res[i] <= 15 && res[i] >= 0) {
					hex.append("0");
				}
				hex.append(Integer.toHexString(0xFF & res[i]));
			}
			s = res.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hex.toString();
	}
	
	private static String MD5(int i) {
		return MD5(i+"");
	}
	
	private static String SHA(String s) {
		StringBuffer hex = new StringBuffer();
		try {
			MessageDigest md5 = MessageDigest.getInstance("SHA");
			md5.update(s.getBytes());
			byte[] res = md5.digest();
			for(int i = 0; i < res.length; i++) {
				if(res[i] <= 15 && res[i] >= 0) {
					hex.append("0");
				}
				hex.append(Integer.toHexString(0xFF & res[i]));
			}
			s = res.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hex.toString();
	}
	
	private static String SHA(int i) {
		return SHA(i+"");
	}
	
	private static String crosssum(int i) {
		String s = ""+i;
		int sum = 0;
		char[] buf = s.toCharArray();
		char[] creator = new char[1];
		
		for(int j = 0; j < buf.length; j++) {
			creator[0] = buf[j];
			if(creator[0] != '-') {
				sum = sum + Integer.parseInt(new String(creator));
			}
		}
		
		return new String(""+sum);
	}
	
	private static String signedCrosssum(int i) {
		if(i < 0) {
			return "-"+crosssum(i);
		} else {
			return crosssum(i);
		}
	}
	
	private static String byteCrosssum(Integer i) {
		byte[] b = toByteArray(i);
		int buf = 0;
		for(int j = 0; j < b.length; j++) {
			buf += b[j];
		}
		return new String(""+buf);
	}
	
	private static byte[] toByteArray(int value) {
		return new byte[]{ (byte)(value >>> 24), (byte)(value >> 16 & 0xff), (byte)(value >> 8 & 0xff), (byte)(value & 0xff) };    
	}
	
	private static void HashTableAdd(int i, HashMethod method) {
		String s = "";
		currentMethod = method;
		if(method == HashMethod.CSB) {
			s = byteCrosssum(i);
		} else if(method == HashMethod.CSS) {
			s = signedCrosssum(i);
		} else if(method == HashMethod.CSU) {
			s = crosssum(i);
		} else if(method == HashMethod.MD5) {
			s = MD5(i);
		} else if(method == HashMethod.SHA) {
			s = SHA(i);
		}
		
		if(map.keySet().contains(s)) {
			Bucket buf = map.get(s);
			if(!buf.contains(i)){
				items++;
				collisions++;
				buf.add(i);
			}
		} else {
			items++;
			map.put(s, new Bucket(i));
		}
		
		if(items == limit*0.5) {
			col50="Collisions at 50%:\t" + collisions + "/" + items +  " (" +collisions*100/items + "%/" +collisions*100/limit + "%)";
		} else if(items == limit*0.9) {
			col90="Collisions at 90%:\t" + collisions + "/" + items +  " (" +collisions*100/items + "%/" +collisions*100/limit + "%)";
		} else if(items == limit*0.95) {
			col95="Collisions at 95%:\t" + collisions + "/" + items +  " (" +collisions*100/items + "%/" +collisions*100/limit + "%)";
		} else if(items == limit) {
			col100="Collisions at 100%:\t" + collisions + "/" + items +  " (" +collisions*100/items + "%/" +collisions*100/limit + "%)";
		}
	}

	public static void main(String[] args) {
		test1();
	}
	
	public static void test1() {
		Random rand = new Random();
		int[] numbers = new int[limit];
		int buf = 0;
		int i = 0;
		
		for(i = 0; i < limit; i++) {
			numbers[i] = rand.nextInt();
		}
		
		map = new LinkedHashMap<String, Bucket>();
		items = 0;
		collisions = 0;
		col50 = "";
		col90 = "";
		col95 = "";
		col100 = "";
		for(i = 0; i < limit; i++) {
			HashTableAdd(numbers[i], HashMethod.CSU);
		}
		System.out.println(getTable());
		printCollisionData();
		
		map = new LinkedHashMap<String, Bucket>();
		items = 0;
		collisions = 0;
		col50 = "";
		col90 = "";
		col95 = "";
		col100 = "";
		for(i = 0; i < limit; i++) {
			HashTableAdd(numbers[i], HashMethod.CSS);
		}
		System.out.println(getTable());
		printCollisionData();
		
		map = new LinkedHashMap<String, Bucket>();
		items = 0;
		collisions = 0;
		col50 = "";
		col90 = "";
		col95 = "";
		col100 = "";
		for(i = 0; i < limit; i++) {
			HashTableAdd(numbers[i], HashMethod.CSB);
		}
		System.out.println(getTable());
		printCollisionData();
		
		map = new LinkedHashMap<String, Bucket>();
		items = 0;
		collisions = 0;
		col50 = "";
		col90 = "";
		col95 = "";
		col100 = "";
		for(i = 0; i < limit; i++) {
			HashTableAdd(numbers[i], HashMethod.MD5);
		}
		System.out.println(getTable());
		printCollisionData();
		
		map = new LinkedHashMap<String, Bucket>();
		items = 0;
		collisions = 0;
		col50 = "";
		col90 = "";
		col95 = "";
		col100 = "";
		for(i = 0; i < limit; i++) {
			HashTableAdd(numbers[i], HashMethod.SHA);
		}
		System.out.println(getTable());
		printCollisionData();
		
	}
	
	private static void sortMap() {
		HashMap<String, Bucket> buf = new LinkedHashMap<String, Bucket>();
		List<String> mapKeys = new ArrayList<String>(map.keySet());
		TreeSet<String> sortedKeys = new TreeSet<String>(mapKeys);
		Object[] sortedArray = sortedKeys.toArray();
		for(int i = 0; i < sortedArray.length; i++) {
			buf.put((String)sortedArray[i], map.get(sortedArray[i]));
		}
		map = buf;
	}
	
	private static void printCollisionData() {
		System.out.println("Method:\t" + currentMethod);
		System.out.println("Entries:\t" + limit);
		System.out.println("Hashes:\t" + map.keySet().size());
		System.out.println(col50);
		System.out.println(col90);
		System.out.println(col95);
		System.out.println(col100);
	}
	
	private static String getTable() {
		StringBuffer res = new StringBuffer();
		sortMap();
		Iterator<String> keys = map.keySet().iterator();
		Iterator<Bucket> vals = map.values().iterator();
		res.append("Key\tValue\n");
		while(vals.hasNext()) {
			res.append(keys.next());
			res.append(vals.next());
		}
		return res.toString();
	}
	
}
