package dhbw.uniquelist;

public enum Beh�lter {
	FLASCHE(true),
	PAPPBEH�LTER(false);
		private boolean etikettierbar = false;
		private Beh�lter(boolean etikettierbar) {
		this.etikettierbar = etikettierbar;
		}
		
		public boolean getEtikettierbar() {
			return this.etikettierbar;
		}
	}
