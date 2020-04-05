package appstate;

public class AppState {
    private UserData userData;
    private MenuState menuState;
    
    public AppState(){
        userData = new UserData();
        menuState = MenuState.CREATION;
    }

    public UserData getUserData() {
        return userData;
    }

    public MenuState getMenuState() {
        return menuState;
    }

    public void setMenuState(MenuState menuState) {
        this.menuState = menuState;
    }
    
}
