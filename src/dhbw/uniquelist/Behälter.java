package dhbw.uniquelist;

public enum Behälter {
	FLASCHE(true),
	PAPPBEHÄLTER(false);
		private boolean etikettierbar = false;
		private Behälter(boolean etikettierbar) {
		this.etikettierbar = etikettierbar;
		}
		
		public boolean getEtikettierbar() {
			return this.etikettierbar;
		}
	}
