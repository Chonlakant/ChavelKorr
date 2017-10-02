package com.twentyfour.chavel.bus.event;

public class Events_State_Menu {


	public static class Events_StateMenuFragmentMessage {

		private String message;

		public Events_StateMenuFragmentMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
