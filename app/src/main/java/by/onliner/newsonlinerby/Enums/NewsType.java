/*
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package by.onliner.newsonlinerby.Enums;

public enum NewsType {
    Autos,
    Peoples,
    Realt,
    Technologies;

    public static NewsType fromInteger(int x) {
        switch (x) {
            case 0:
                return Autos;
            case 1:
                return Peoples;
            case 2:
                return Realt;
            case 3:
                return Technologies;
        }
        return null;
    }
}