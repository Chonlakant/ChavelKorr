package com.twentyfour.chavel.bus.event;

import com.twentyfour.chavel.model.DetailsRouteModel;

public class Events_Route_Details {


	public static class Events_RoutDetails {

		private DetailsRouteModel message;

		public Events_RoutDetails(DetailsRouteModel message) {
			this.message = message;
		}

		public DetailsRouteModel getMessage() {
			return message;
		}
	}
}
