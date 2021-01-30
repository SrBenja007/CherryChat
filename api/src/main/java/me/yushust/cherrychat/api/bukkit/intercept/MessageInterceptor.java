package me.yushust.cherrychat.api.bukkit.intercept;

import org.bukkit.entity.Player;

public interface MessageInterceptor {

  boolean intercept(Player sender, String message, boolean command);

}
