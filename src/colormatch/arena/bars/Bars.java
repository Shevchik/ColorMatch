/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package colormatch.arena.bars;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Bars {

	public static String waiting = "&6Ожидаем игроков, текущее количество:&r {COUNT}";
	public static String starting = "&6Игра начнётся через :&r {SECONDS} секунд";
	public static String playing = "&6Игроков в игре:&r {COUNT}";

	private static final HashMap<Player, BossBar> bossbars = new HashMap<Player, BossBar>();

	public static void setBar(Player player, String message, int count, int seconds, float percent) {
		try {
			message = message.replace("{COUNT}", String.valueOf(count));
			message = message.replace("{SECONDS}", String.valueOf(seconds));
			message = ChatColor.translateAlternateColorCodes('&', message);
			BossBar bar = bossbars.get(player);
			if (bar == null) {
				bar = Bukkit.createBossBar(message, BarColor.PINK, BarStyle.SOLID);
				bar.setProgress(percent / 100.0F);
				bar.addPlayer(player);
				bossbars.put(player, bar);
			} else {
				bar.setTitle(message);
				bar.setProgress(percent / 100.0F);
			}
		} catch (Throwable t) {
		}
	}

	public static void removeBar(Player player) {
		try {
			BossBar bar = bossbars.remove(player);
			if (bar != null) {
				bar.removePlayer(player);
			}
		} catch (Throwable t) {
		}
	}

}
