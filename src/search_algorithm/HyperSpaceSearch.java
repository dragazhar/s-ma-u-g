package search_algorithm;

public class HyperSpaceSearch {

    public static void initMVerse(Multiverse mVerse, double pc1, double pc2) {
	for (Universe u : mVerse.parallelUniverses) {
	    u.generateRNDPoints(pc1, pc2);
	   // System.out.println(u.toString2());
	}
    }

    public static Universe search(Multiverse mVerse) {
	Universe bUniverse = null;
	int fne = 0;
	// find first not explored space
	for (int i = 0; i < mVerse.getSizeofMultiverse(); i++) {
	    if (!mVerse.parallelUniverses.get(i).explored) {
		bUniverse = mVerse.parallelUniverses.get(i);
		fne = i;
		break;
	    }
	}

	for (int i = fne; i < mVerse.getSizeofMultiverse(); i++) {
	    Universe currentUniverse = mVerse.parallelUniverses.get(i);

	   /* if ((bUniverse.getAverageMass() < currentUniverse.getAverageMass())
		    && (!currentUniverse.explored)) {
		bUniverse = currentUniverse;
	    }*/

	   
	      if ((bUniverse.getMaxMass() < currentUniverse.getMaxMass()) &&
	      (!currentUniverse.explored)) { bUniverse = currentUniverse; }
	    
	}

	return bUniverse;
    }

    public static Universe searchByFlag(Multiverse mVerse, int[] flag) {
	Universe bUniverse = null;
	
	for (int i = 0; i < mVerse.getSizeofMultiverse(); i++) {
	
	    if (mVerse.parallelUniverses.get(i).compareFlags(flag)) {
		bUniverse = mVerse.parallelUniverses.get(i);
		
	    }

	}

	return bUniverse;
    }

}
