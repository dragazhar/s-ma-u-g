package search_algorithm;

public class Key implements Comparable<Key> {
    private Long keyT = (long) 0;
    private Long keyO = (long) 0;
    private Long keyU = (long) 0;

    /**
     * 
     * Key U-universe number, T- transition function code, O - outputfunction code
     * @param key1
     * @param key2
     * @param key3
     */
    public Key(long key1, long key2, long key3) {
	this.keyU = key1;
	this.keyT = key2;
	this.keyO = key3;

    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
	if (!(o instanceof Key))
	    return false;
	Key k = (Key) o;
	return k.keyU.equals(keyU)&&k.keyT.equals(keyT) && k.keyO.equals(keyO);
    }

    public int compareTo(Key k) {
	int tCmp = keyT.compareTo(k.keyT);
	int oCmp = keyO.compareTo(k.keyO);
	int uCmp = keyU.compareTo(k.keyU);
	int v;
	if ((uCmp==0)){
	    if ((tCmp==0)){
		v=oCmp;
	    }else
		v=tCmp;
	}else
	    v=uCmp;
	
	return v;
    }

    public int hashCode() {
	return 31 * keyT.hashCode()+keyU.hashCode()/17 + keyO.hashCode();
    }
    public long getKeyU() {
	return keyU;
    }

    public void setKeyU(long keyU) {
	this.keyU = keyU;
    }


    public long getKeyT() {
	return keyT;
    }

    public void setKeyT(long keyT) {
	this.keyT = keyT;
    }

    public long getKeyO() {
	return keyO;
    }

    public void setKeyO(long keyO) {
	this.keyO = keyO;
    }

    @Override
    public String toString() {
	return "(" + keyU + ", " + keyT + ", " + keyO + ")";
    }

}
