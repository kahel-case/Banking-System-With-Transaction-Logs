package sys.collection;

public class EnumCollection {
    public static final String Active = "Active";
    public static final String Blocked = "Blocked";

    public enum Launcher {
        SIGN_IN, CREATE_ACCOUNT, MAIN_PAGE
    }

    public enum MainApp {
        MAIN_PAGE, DEPOSIT_PAGE, WITHDRAW_PAGE, TRANSFER_PAGE, HISTORY_PAGE
    }
}
