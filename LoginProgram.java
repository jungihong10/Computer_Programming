import java.util.ArrayList;
import java.util.Scanner;

// 회원 클래스
class Member {
    private String id;
    private String password;

    public Member(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Password: " + password;
    }
}

// 관리자 클래스
class Admin extends Member {
    private ArrayList<Member> memberList;

    public Admin(String id, String password, ArrayList<Member> memberList) {
        super(id, password);
        this.memberList = memberList;
    }

    public void printMemberList() {
        System.out.println("**********************");
        System.out.println();
        System.out.println("현재 등록된 모든 회원들의 정보입니다.");
        int totalMembers = memberList.size();
        if (totalMembers == 1) {
            System.out.println("전체 회원 수: 0");
            System.out.println("**********************");
        System.out.println();
        } else {
            for (Member member : memberList) {
                if (!member.getId().equalsIgnoreCase("admin")) {
                    System.out.println(member);
                }
            }
            System.out.println("전체 회원 수: " + (totalMembers - 1));
            System.out.println("**********************");
            System.out.println();
        }
    }
}

// 로그인 프로그램 클래스
public class LoginProgram {
    private Member loggedInUser;
    private ArrayList<Member> memberList;

    public LoginProgram() {
        memberList = new ArrayList<>();
        Admin admin = new Admin("admin", "admin", memberList);
        memberList.add(admin);
    }

    public void registerMember() {
        System.out.println("**********************");
        System.out.println();
        System.out.println("회원가입을 진행합니다.");
        System.out.println("** 주의 ** 아이디와 비밀번호는 알파벳 대소문자와 숫자로만 이뤄져야 합니다.");
        System.out.println("** 주의 ** 아이디는 대소문자를 구분하지 않습니다.");
        System.out.println("** 주의 ** 비밀번호는 10자 이상이여야 합니다.");
        Scanner scanner = new Scanner(System.in);
        String id;
        boolean isExisting;
        do {
            System.out.print("아이디를 입력하세요: ");
            id = scanner.nextLine();
            isExisting = isExistingId(id);
            if (isExisting) {
                System.out.println("이미 존재하는 회원 아이디입니다.");
            }
        } while (isExisting);

    
        String password;
        boolean isValidLength;
        boolean hasSpecialChar;

        do {
            System.out.print("비밀번호를 입력하세요: ");
            password = scanner.nextLine();

            isValidLength = password.length() >= 10;
            hasSpecialChar = !password.matches("[a-zA-Z0-9]+");

            if (!isValidLength && hasSpecialChar) {
                System.out.println("길이가 너무 짧습니다.");
                System.out.println("특수 문자는 포함할 수 없습니다.");
                System.out.println("알파벳 대소문자와 숫자로만 입력해주세요.");
                System.out.println("");

            } else if (!isValidLength) {
                System.out.println("길이가 너무 짧습니다.");
                System.out.println("");

            } else if (hasSpecialChar) {
                System.out.println("특수 문자는 포함할 수 없습니다.");
                System.out.println("알파벳 대소문자와 숫자로만 입력해주세요.");
                System.out.println("");
            }
        } while (!isValidLength || hasSpecialChar);

        Member newMember = new Member(id, password);
        memberList.add(newMember);
        System.out.println(id+ "님 회원 가입을 축하합니다.");
        System.out.println("**********************");
        System.out.println();

    }

    private boolean isExistingId(String id) {
        for (Member member : memberList) {
            if (member.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 10;
    }

    public boolean login() {
        System.out.println("**********************");
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        System.out.print("아이디를 입력하세요: ");
        String id = scanner.nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.nextLine();
        
        for (Member member : memberList) {
            if (member.getId().equalsIgnoreCase(id)) {
                if (member.getPassword().equals(password)) {
                    loggedInUser = member;
                    if (member instanceof Admin) {
                        System.out.println("**********************");
                        System.out.println();
                        System.out.println("admin 계정으로 로그인 되었습니다.");
                    } else {
                        System.out.println("**********************");
                        System.out.println();
                        System.out.println(member.getId() + " 계정으로 로그인 되었습니다.");
                    }
                    return true;
                } else {

                    System.out.println("비밀번호가 일치하지 않습니다.");
                    System.out.println("**********************");
                    System.out.println();
                    return false;
                }
            }
        }
        

        System.out.println("존재하지 않는 회원 아이디입니다.");
        System.out.println("회원가입을 진행해 주시길 바랍니다.");
        System.out.println("**********************");
        System.out.println();

        return false;
    }

    public void logout() {
        loggedInUser = null;
        System.out.println("**********************");
        System.out.println();
        System.out.println("로그아웃 되었습니다.");
        System.out.println("**********************");
        System.out.println();
        System.out.println("**********************");
        System.out.println();
    }

    public void deleteAccount() {
        memberList.remove(loggedInUser);
        loggedInUser = null;
        System.out.println("**********************");
        System.out.println();
        System.out.println("탈퇴가 완료되었습니다.");
        System.out.println("이용해 주셔서 감사합니다.");
        System.out.println("**********************");
        System.out.println();
        System.out.println("**********************");
        System.out.println();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isFirstLoop = true;

        while (true) {
            
            if (isFirstLoop) {
                System.out.println("어서오세요. 간단한 로그인 프로그램입니다.");
                System.out.println("원하시는 업무를 선택해 주세요.");
                System.out.println("1. 로그인 2. 회원가입 3. 종료");
                System.out.print("선택하기 : ");
                isFirstLoop = false;
            } else {
                
                System.out.println("원하시는 업무를 선택해 주세요.");
                System.out.println("1. 로그인 2. 회원가입 3. 종료");
                System.out.print("선택하기 : ");

            }
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (login()) {
                        if (loggedInUser instanceof Admin) {
                            Admin admin = (Admin) loggedInUser;
                            while (true) {
                                
                                System.out.println("원하시는 업무를 선택해 주세요");
                                System.out.println("1. 전체 회원 조회 2. 로그아웃");
                                System.out.print("선택하기 :");
                                String adminChoice = scanner.nextLine();
                                if (adminChoice.equals("1")) {
                                    admin.printMemberList();
                                } else if (adminChoice.equals("2")) {
                                    logout();
                                    break;
                                } else {
                                    System.out.println("**********************");
                                    System.out.println();
                                    System.out.println("잘못 입력하셨습니다. 1 ~ 2 숫자를 입력해 주세요");
                                    System.out.println("**********************");
                                    System.out.println();
                                }
                            }
                        } else {
                            while (true) {
                               

                                System.out.println("원하시는 업무를 선택해 주세요");
                                System.out.println("1. 탈퇴하기 2. 로그아웃");
                                System.out.print("선택하기 :");
                                String userChoice = scanner.nextLine();
                                if (userChoice.equals("1")) {
                                    deleteAccount();
                                    break;
                                } else if (userChoice.equals("2")) {
                                    logout();
                                    break;
                                } else {
                                    System.out.println("**********************");
                                    System.out.println();
                                    System.out.println("잘못 입력하셨습니다. 1 ~ 2 숫자를 입력해 주세요");
                                }
                            }
                        }
                    }
                    break;
                case "2":
                    registerMember();
                    break;
                case "3":
                    System.out.println("**********************");
                    System.out.println();
                    System.out.println("**********************");
                    System.out.println();
                    System.out.println("로그인 프로그램을 종료합니다.");
                    System.out.println("**********************");

                    return;
                default:
                    System.out.println("**********************");
                    System.out.println();
                    System.out.println("잘못 입력하셨습니다. 1 ~ 2 숫자를 입력해 주세요");
                break;
            }
        }
    }

    public static void main(String[] args) {
        LoginProgram program = new LoginProgram();
        program.run();
    }
}
