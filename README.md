# BungeeCommandR
BungeeCord から Spigot にコマンドを送信する物

Spigot には `net.simplyrin.bungeecommand.spigot.Main` を何らかの形で読み込んで頂くか以下のプラグインを導入してください

- [Releases](https://github.com/SimplyRin/BungeeCommandR/releases)

# For example
```Java
// BungeeCord
@Override
public void onEnable() {
  BungeeCommand.registerThis();
}

@Override
public void onDisable() {
  BungeeCommand.unregisterThis();
}

public void test() {
  BungeeCommand.dispatchCommand(server, command);
}
```
