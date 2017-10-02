package com.twentyfour.chavel.bus.event;

public class Events_Route_Period {


	public static class Events_RoutPeriodFragmentMessage {

		private String message;

		public Events_RoutPeriodFragmentMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
