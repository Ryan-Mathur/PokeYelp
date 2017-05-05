package pokeyelp.grat.team.pokemonyelp;

import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

/**
 * Created by Admin on 5/4/17.
 */

public class UiAutomatorUtils {

    public static final String TEXT_ALLOW = "Allow";
    public static final String TEXT_DENY = "Deny";
    public static final String TEXT_NEVER_ASK_AGAIN = "Never ask again";
    public static final String TEXT_PERMISSIONS = "Permissions";

    private UiAutomatorUtils() {
        // no instances
    }

    // Navigation

    public static void openPermissions(UiDevice device) throws UiObjectNotFoundException {
        UiObject permissions = device.findObject(new UiSelector().text(TEXT_PERMISSIONS));
        permissions.click();
    }

    // Assertions

    public static void assertViewWithTextIsVisible(UiDevice device, String text) {
        UiObject allowButton = device.findObject(new UiSelector().text(text));
        if (!allowButton.exists()) {
            throw new AssertionError("View with text <" + text + "> not found!");
        }
    }

    // Actions

    public static void allowCurrentPermission(UiDevice device) throws UiObjectNotFoundException {
        UiObject allowButton = device.findObject(new UiSelector().text(TEXT_ALLOW));
        allowButton.click();
    }

    public static void denyCurrentPermission(UiDevice device) throws UiObjectNotFoundException {
        UiObject denyButton = device.findObject(new UiSelector().text(TEXT_DENY));
        denyButton.click();
    }

    public static void denyCurrentPermissionPermanently(UiDevice device) throws UiObjectNotFoundException {
        UiObject neverAskAgainCheckbox = device.findObject(new UiSelector().text(TEXT_NEVER_ASK_AGAIN));
        neverAskAgainCheckbox.click();
        denyCurrentPermission(device);
    }

    public static void grantPermission(UiDevice device, String permissionTitle) throws UiObjectNotFoundException {
        UiObject permissionEntry = device.findObject(new UiSelector().text(permissionTitle));
        permissionEntry.click();
    }
}
