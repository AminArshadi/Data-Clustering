import java.util.*;
import java.io.*;

class Main {

	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(new File("yellow_tripdata_2009-01-15_1hour_clean.csv"));

		//ArrayList<TripRecord> tripRecordSet = new ArrayList<>();
		ArrayList<GPScoord> gpsCoordSet = new ArrayList<>();

		boolean flag = true;

		while (scanner.hasNext()) {

			if (flag) { // skiping the first line which contains column titles
				scanner.nextLine();
				flag = false;
				continue;
			}

			String line = scanner.nextLine();
			String[] splitLine = line.split(",");

			String pickupDateTime = splitLine[4];

			double pickupLon = Double.parseDouble(splitLine[8]);
			double pickupLat = Double.parseDouble(splitLine[9]);
			GPScoord pickupLocation = new GPScoord(pickupLon, pickupLat);

			double dropoffLon = Double.parseDouble(splitLine[12]);
			double dropoffLat = Double.parseDouble(splitLine[13]);
			GPScoord dropoffLocation = new GPScoord(dropoffLon, dropoffLat);

			float tripDistance = Float.parseFloat(splitLine[7]);

			gpsCoordSet.add(pickupLocation);
			//tripRecordSet.add( new TripRecord(pickupDateTime, pickupLocation, dropoffLocation, tripDistance) );
		}


		System.out.println("Enter the eps (of type double): ");

		Scanner input = new Scanner(System.in);
        double eps = Double.parseDouble(input.nextLine());

        System.out.println("Enter the minPts (of type integer): ");

		input = new Scanner(System.in);
        int minPts = Integer.parseInt(input.nextLine());

        System.out.println("Please wait . . .");


		DBSCAN dbscan = new DBSCAN(gpsCoordSet, eps, minPts); // 0.0001, 5

		ArrayList<Cluster> clusters = dbscan.getClusters();

		try ( PrintWriter writer = new PrintWriter("output.csv") ) { // writing into csv file
		
			StringBuilder line = new StringBuilder();

			line.append("Cluster ID");
			line.append(',');
			line.append("Longtitude");
			line.append(',');
			line.append("Latitude");
			line.append(',');
			line.append("Number of points");
			line.append('\n');

			writer.write(line.toString());

			int counter = 1;

			for (Cluster cluster : clusters) {

				line = new StringBuilder();

				line.append(counter++);
				line.append(',');
				line.append(cluster.getPosition().getLon());
				line.append(',');
				line.append(cluster.getPosition().getLat());
				line.append(',');
				line.append(cluster.getSize());
				line.append('\n');

				writer.write(line.toString());

				//System.out.println(counter + "  " + cluster.getPosition().getLon() + "  " + cluster.getPosition().getLat() + "  " + cluster.getSize());
			}
		}

		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("The csv file is now updated!");
	}

}
