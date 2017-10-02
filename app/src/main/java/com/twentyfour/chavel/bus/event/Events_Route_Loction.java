package com.twentyfour.chavel.bus.event;

import com.twentyfour.chavel.model.Location;

public class Events_Route_Loction {


	public static class Events_RoutLocationFragmentMessage {

		private Location message;

		public Events_RoutLocationFragmentMessage(Location message) {
			this.message = message;
		}

		public Location getMessage() {
			return message;
		}
	}
}
