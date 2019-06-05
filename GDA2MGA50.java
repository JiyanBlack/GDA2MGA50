public static class WGS84toMGA50 {

		private double semiMajorAxis = 6378137;
		private double inverseFlattening = 298.257222101;
		private double falseEasting = 500000;
		private double falseNorthing = 10000000;
		private double centralScaleFactor = 0.9996;
		private double zoneWidth = 6;
		private double CentralLon = -177;
		private double centralMeridian = 117;
		private double K10= 2/inverseFlattening - Math.pow(1/inverseFlattening, 2);

		public WGS84toMGA50() {
		}

		public double[] transformXY(double inx, double iny) {
			double L4 = iny;
			double F4 = inx;
			double G4 = (F4 / 180.0) * Math.PI;
			double M4 = (L4 / 180.0) * Math.PI;
			double C6 = centralMeridian;
			double L6 = L4 - C6;
			double M6 = (L6 / 180) * Math.PI;
			double K11 = K10 * K10; //e4
			double K12 = K10 * K11; //e6
			double E9 = Math.sin(G4);
			double E10 = Math.sin(2 * G4);
			double E11 = Math.sin(4 * G4);
			double E12 = Math.sin(6 * G4);
			double K15 = semiMajorAxis * (1 - K10) / Math.pow((1 - (K10 * E9 * E9)), 1.5); //Rho
			double K16 = semiMajorAxis / Math.pow((1 - (K10 * E9 * E9)), 0.5); //Nu
			double M9 = 1 - (K10 / 4) - ((3 * K11) / 64) - ((5 * K12) / 256); //A0
			double M10 = (3 / 8) * (K10 + (K11 / 4) + ((15 * K12) / 128)); //A2
			double M11 = (15 / 256) * (K11 + ((3 * K12) / 4)); // A4
			double M12 = (35 * K12) / 3072; //A6
			double E15 = semiMajorAxis * M9 * G4; // Meridian Distance 1st term
			double E16 = semiMajorAxis * M10 * E10; //2ND TERM
			double E17 = semiMajorAxis * M11 * E11; // 3RD
			double E18 = semiMajorAxis * M12 * E12; // 4TH
			double E19 = E15 + E16 + E17 + E18; // SUM
			double K22 = Math.tan(G4);
			double K23 = Math.pow(K22, 2);
			double K25 = Math.pow(K22, 4);
			double K27 = Math.pow(K22, 6);
			double M22 = K16 / K15;
			double M23 = Math.pow(M22, 2);
			double M24 = Math.pow(M22, 3);
			double M25 = Math.pow(M22, 4);
			double H22 = M6;
			double H23 = Math.pow(H22, 2);
			double H24 = Math.pow(H22, 3);
			double H25 = Math.pow(H22, 4);
			double H26 = Math.pow(H22, 5);
			double H27 = Math.pow(H22, 6);
			double H28 = Math.pow(H22, 7);
			double H29 = Math.pow(H22, 8);
			double E22 = Math.cos(G4);
			double E23 = Math.pow(E22, 2);
			double E24 = Math.pow(E22, 3);
			double E25 = Math.pow(E22, 4);
			double E26 = Math.pow(E22, 5);
			double E27 = Math.pow(E22, 6);
			double E28 = Math.pow(E22, 7);
			double E33 = K16 * H22 * E22; //Easting 1st term
			double E34 = K16 * H24 * E24 * (M22 - K23) / 6;
			double E35 = K16 * H26 * E26 * (4 * M24 * (1 - 6 * K23) + M23 * (1 + 8 * K23) - M22 * (2 * K23) + K25) / 120;
			double E36 = K16 * H28 * E28 * (61 - 479 * K23 + 179 * K25 - K27) / 5040;
			double E37 = E33 + E34 + E35;
			double E38 = centralScaleFactor * E37;
			double x = E38 + falseEasting;
			// done calc x, start y
			double K32 = E19; //meridian Dist
			double K33 = K16 * E9 * H23 * E22 / 2;
			double K34 = K16 * E9 * H25 * E24 * (4 * M23 + M22 - K23) / 24;
			double K35 = K16 * E9 * H27 * E26 * (8 * M25 * (11 - 24 * K23) - 28 * M24 * (1 - 6 * K23) + M23 * (1 - 32 * K23) - M22 * (2 * K23) + K25) / 720;
			double K36 = K16 * E9 * H29 * E28 * (1385 - 3111 * K23 + 543 * K25 - K27) / 40320;
			double K37 = K32 + K33 + K34 + K35 + K36;
			double K38 = centralScaleFactor * K37;
			double y = K38 + falseNorthing;
			return new double[] {x,y};
		}
}