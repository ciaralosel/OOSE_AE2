package detectors;

public class CommentReport {
	private String type;
	private String text;
	private int lineNumber;
	private boolean isOrphan;
	
	public CommentReport(String type, String text, int lineNumber, boolean isOrphan) {
		this.type = type;
		this.text = text;
		this.lineNumber = lineNumber;
		this.isOrphan = isOrphan;
	}
	@Override
	public String toString() {
		return lineNumber + "|" + type + "|" + isOrphan + "|" + text.replaceAll("\\n", "").trim();
	}
}
