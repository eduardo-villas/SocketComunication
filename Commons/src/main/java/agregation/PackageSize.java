package agregation;

import java.util.Arrays;
import java.util.List;

public enum PackageSize {

	ZERO_KB(0),
	ONE_KB(1024),
	TWO_KB(1024*2),
	FOUR_KB(1024*4),
	EIGHT_KB(1024*8),
	SIXTEEN_KB(1024*16);
	
	private int size;
	private static List<PackageSize> values = Arrays.asList(PackageSize.values()); 

	private PackageSize(int size) {
		this.size = size;
	}

	public static boolean isValidSize(int agregationPacketSize) {
		return values.stream().anyMatch(p -> p.size == agregationPacketSize);
	}

	public static String valuesFormat() {
		return values.stream().map(p -> Integer.toString(p.size)).reduce("", (a, b) -> a.isEmpty() ? b: a + ", " + b );
	}
}
