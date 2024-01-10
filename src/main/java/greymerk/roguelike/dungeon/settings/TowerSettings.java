package greymerk.roguelike.dungeon.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import greymerk.roguelike.dungeon.towers.Tower;
import greymerk.roguelike.theme.ITheme;
import greymerk.roguelike.theme.Theme;
import greymerk.roguelike.theme.ThemeOak;

public class TowerSettings {

    private final Tower tower;
    private final ITheme theme;

    public TowerSettings(Tower tower, ITheme theme) {
        this.tower = tower;
        this.theme = theme;
    }

    public TowerSettings(JsonElement e) {

        JsonObject data = e.getAsJsonObject();
        this.tower = data.has("type") ? Tower.valueOf(data.get("type").getAsString()) : Tower.ROGUE;
        this.theme = data.has("theme") ? Theme.create(data.get("theme").getAsJsonObject()) : new ThemeOak();

    }

    public ITheme getTheme() {
        return this.theme;
    }

    public Tower getTower() {
        return this.tower;
    }
}
