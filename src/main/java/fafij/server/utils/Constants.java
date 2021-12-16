package fafij.server.utils;

public class Constants {

    public static final long KidRole = 3L;
    public static final long AdultRole = 2L;
    public static final long AdminRole = 1L;

    public class User {
        public static final String category = "Food";
        public static final String comment = "Food for me";
        public static final String journal = "Ilya'sJournal";
        public static final String login = "ilyaPopenkoOlegovich";
        public static final String password = "a82f68c86d10821d4518fa90f2dd4cd1";
        public static final String date = "06.12.2022 19:33:00";
        public static final long role = Constants.AdminRole;
        public static final long sum = 2000;
    }

    public class NewNote {
        public static final String category = "Food";
        public static final String comment = "Goods for me";
        public static final String date = "10.11.2025 20:00:00";
        public static final long sum = 5000;
    }

    public class UserDB {
        public static final long idNote = 26;
        public static final String category = "Games";
        public static final String comment = "Покупка игры";
        public static final String journal = "BatalinJournal";
        public static final String login = "AlexanderBatalin";
        public static final String password = "53ad7b7d0b08c1caa1fcd8fdee76c225";
        public static final String date = "06.12.2021 20:11:45";
        public static final long sum = -1999;
    }

    public class Path {
        public static final String deleteNotePath = "/deleteNote";
        public static final String listCategoryPath = "/listCategory";
        public static final String addCategoryPath = "/addCategory";
        public static final String deleteCategoryPath = "/deleteCategory";
        public static final String listNotePath = "/listNote";
        public static final String privatePath = "/private";
        public static final String createJournalPath = "/createJournal";
        public static final String registrationPath = "/registration";
        public static final String loginPath = "/login";
        public static final String addNotePath = "/addNote";
        public static final String updateNotePath = "/updateNote";
        public static final String userJournalsPath = "/userJournals";
        public static final String invitationPath = "/invitations";
        public static final String acceptPath = "/accept";
    }
}
