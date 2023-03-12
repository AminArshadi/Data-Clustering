import java.util.*;
import java.lang.Math;

class Cluster {

	private ArrayList<GPScoord> pointSet;

	public Cluster() {
		pointSet = new ArrayList<>();
	}

	public Cluster(ArrayList<GPScoord> gpsCoords) {
		pointSet = new ArrayList<>();
		pointSet.addAll(gpsCoords);
	}

	protected void addPoint(GPScoord point) {
		pointSet.add(point);
	}

	protected ArrayList<GPScoord> getPointSet() {
		return pointSet;
	}

	protected GPScoord getPosition() { // Avarage value of the GPS coordinates of its point sets
		double lonSum = 0;
		double latSum = 0;

		for (GPScoord point: pointSet) {
			lonSum += point.getLon();
			latSum += point.getLat();
		}

		return new GPScoord( lonSum/getSize(), latSum/getSize() );
	}

	protected int getSize() {
		return pointSet.size();
	}

	protected String print() {
		StringBuffer buffer = new StringBuffer("[");

		boolean flag = false;

		for (GPScoord point : pointSet) {

			if (flag)
				buffer.append(", ");

			flag = true;

			buffer.append(point.print());
		}
		
		buffer.append("]");

		return buffer.toString();
	}
}
