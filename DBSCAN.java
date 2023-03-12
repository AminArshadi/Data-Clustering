import java.util.*;
import java.awt.geom.Point2D;
import java.lang.*;

class DBSCAN {

	final private ArrayList<GPScoord> gpsCoords;
	final private double eps;
	final private int minPts;

	public DBSCAN(ArrayList<GPScoord> gpsCoords, double eps, int minPts) {
		this.gpsCoords = gpsCoords;
		this.eps = eps;
		this.minPts = minPts;
	}

	protected ArrayList<Cluster> getClusters() {

		HashMap<GPScoord, String> labels = new HashMap<>();

		// flagging all points ans not visited initially
		for (GPScoord point : this.gpsCoords) {
			labels.put(point, "Not Visited");
		}
		//
		int c = 0;

		for (GPScoord gpsCoord : this.gpsCoords) {

			if ( ! (labels.get(gpsCoord).equals("Not Visited")) )
				continue;

			ArrayList<GPScoord> neighbors = getNeighbors(gpsCoord);

			if (neighbors.size() < this.minPts){
				labels.put(gpsCoord, "Noise");
				continue;
			}
				
			labels.put(gpsCoord, Integer.toString(++c));

			ArrayList<GPScoord> s = new ArrayList<>();
			s.addAll(neighbors);

			int counter = 0;

			while ( counter < s.size() ) {

				GPScoord q = s.get(counter++);

				if (labels.get(q).equals("Noise"))
					labels.put(q, Integer.toString(c));

				if ( ! (labels.get(q).equals("Not Visited")) )
					continue;

				labels.put(q, Integer.toString(c));

				ArrayList<GPScoord> neighbors2 = getNeighbors(q);

				if (neighbors2.size() >= this.minPts)
					s.addAll(neighbors2);
			}
		}

		ArrayList<Cluster> clusters = new ArrayList<Cluster>();

		Cluster cluster;

		for (int numCluster=0; numCluster < c; numCluster++) {

			cluster = new Cluster();

			for (Map.Entry mapElement : labels.entrySet()) {

				GPScoord key = (GPScoord) mapElement.getKey();
				String value = (String) mapElement.getValue();

				if ( (! value.equals("Noise")) && (Integer.parseInt(value) == numCluster+1) )
					cluster.addPoint(key);
			}

			clusters.add(cluster);
		}
		
		return clusters;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///// Helper //////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private ArrayList<GPScoord> getNeighbors(GPScoord mainGPSCoord) {

		ArrayList<GPScoord> neighbors = new ArrayList<>();

		for (GPScoord gpsCoord : this.gpsCoords) {

			if ( (gpsCoord != mainGPSCoord) && (distanceBetween(mainGPSCoord, gpsCoord) <= this.eps) ) {

				neighbors.add(gpsCoord);
			}
		}

		return neighbors;
	}


	private double distanceBetween(GPScoord gpsCoord1, GPScoord gpsCoord2) {

		return Point2D.distance(gpsCoord1.getLon(), gpsCoord1.getLat(), gpsCoord2.getLon(), gpsCoord2.getLat());
	}

}
