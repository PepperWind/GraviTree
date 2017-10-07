package fr.flavi1.gravitree.Tools;

import fr.flavi1.gravitree.manager.ScoreManager;

/**
 * Created by Flavien on 24/06/2015.
 */
public class Items {

    public static final int[][] prices = {
        {
                100,
                3000,
                4000,
                30000,
                70000,
                40000,
                300000,
                1000000,
                400000,
                3000000,
                10000000,
                4000000,
                1000000000
        },
        {
                250,
                100000,
                1000,
                2000000,
                100000,
                2000,
                2000000,
                300000,
                10000,
                3000000,
                10000000,
                10000000,
                1000000000
        },
        {
                100,
                50000,
                1000,
                500000,
                10000,
                10000,
                5000000,
                100000,
                100000,
                50000000,
                10000000,
                1000000,
                1000000000
        }
    };

    public static final long[][] durations = {
        {
                1000,
                3000,
                4000,
                30000,
                70000,
                40000,
                300000,
                1000000,
                400000,
                3000000,
                10000000,
                4000000,
                100000000
        },
        {
                2000,
                100000,
                1000,
                2000000,
                100000,
                100000,
                2000000,
                300000,
                1000000,
                3000000,
                10000000,
                10000000,
                100000000
        },
        {
                1000,
                50000,
                1000,
                500000,
                10000,
                10000,
                5000000,
                100000,
                100000,
                50000000,
                10000000,
                1000000,
                100000000
        }
    };


    public static final int[][][][] itemPrequerisites = {
        {
                {},
                {{0, 0}},
                {{0, 0}},
                {{0, 1}},
                {{0, 0}},
                {{0, 2}},
                {{0, 3}},
                {{0, 4}},
                {{0, 5}},
                {{0, 6}},
                {{0, 7}},
                {{0, 8}},
                {{0, 9}, {0, 10}, {0, 11}},
        },

        {
                {},
                {{1, 0}},
                {{1, 0}},
                {{1, 1}},
                {{1, 0}},
                {{1, 2}},
                {{1, 3}},
                {{1, 4}},
                {{1, 5}},
                {{1, 6}},
                {{1, 7}},
                {{1, 8}},
                {{1, 9}, {1, 10}, {1, 11}},
        },

        {
                {},
                {{2, 0}},
                {{2, 0}},
                {{2, 1}},
                {{2, 0}},
                {{2, 2}},
                {{2, 3}},
                {{2, 4}},
                {{2, 5}},
                {{2, 6}},
                {{2, 7}},
                {{2, 8}},
                {{2, 9}, {2, 10}, {2, 11}},
        }

    };

    public static final float[] magnetForce = {
            10f,
            20f,
            30f,
            40f
    };

    public static final String[] buttonDescription = {
            "Middle tree",
            "Left tree",
            "Right tree",
            "A bigger tree",
            "A bigger tree",
            "A bigger tree",
            "A bigger tree",
            "A bigger tree",
            "A bigger tree",
            "A nest",
            "A nest",
            "A nest",
            "Many nests",
            "Wheelbarrow : 30min",
            "Special",
            "Magnet",
            "Special +",
            "Capacity : 1h30",
            "Magnet +",
            "Special ++",
            "Capacity : 3h",
            "Magnet ++",
            "Special +++",
            "Capacity : 6h",
            "Magnet +++",
            "SUPER MAGNET + 12h",
            "Apple",
            "1 bounce",
            "Explosion +",
            "2 bounces",
            "Apple +",
            "Explosion++",
            "3 bounces",
            "Apple ++",
            "Explosion +++",
            "4 bounces",
            "Apple +++",
            "Explosion++++",
            "Golden apple"
    };

    public static final String[] buttonExplanation = {
            "Grows a tree",
            "Grows a tree",
            "Grows a tree",
            "",
            "",
            "",
            "",
            "",
            "",
            "Attracts birds, which will throw \"bombs\"",
            "Attracts birds, which will throw \"bombs\"",
            "Attracts birds, which will throw \"bombs\"",
            "Many more nests",
            "You earn score while you are not playing",
            "Starts an earthquake, or throws a huge bomb\nCooldown : 6h",
            "Attracts apples and birds' \"bombs\"",
            "Increases intensity\nCooldown : 3h",
            "Increases the time you don't lose",
            "Increases the magnet's strength",
            "Increases intensity\nCooldown : 1h30",
            "",
            "",
            "Increases intensity\nCooldown : 30min",
            "",
            "",
            "SUPER MAGNET + 12h",
            "Enables apple generation by the trees.\nApples will fall on Newton's head",
            "Allows the apples to bounce on Newton.\nIt will almost double your gains",
            "When exploding, apples release more particles.\nParticles give you score",
            "",
            "Generates more powerful apples.",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "The most powerful apple that ever grew"
    };


    public static final int[] birdSpawnPeriod = {
            3000,
            3000,
            3000,
            3000,
            3000,
            3000
    };

    public static final int[] appleDamage = {
            10, 20, 35, 50, 500
    };

    public static final int[][][] nestCoords = { // treeId
            {
                    {10, 447},
                    {151, 512},
                    {121, 431}
            },
            {
                    {120, 242},
                    {234, 239},
                    {162, 353}
            },
            {
                    {281, 549},
                    {379, 594},
                    {247, 655}
            }
    };

    public static final int[] earthquakeDurations = {
            1000,
            2000,
            3000,
            4000
    };

    public static final long[] specialCooldown = {
            21600000,
            10800000,
            5400000,
            1800000
    };

    public static final int[] bombValue = {
            4000,
            20000,
            60000,
            100000
    };


    public static final int[] birdAttackPeriods = {
        1000,
        500
};

    public static boolean arePrerequiesitesFilled(int subMenuId, int id) {
        if(itemPrequerisites[subMenuId].length <= id)
            return true;

        if(itemPrequerisites[subMenuId][id].length == 0)
            return true;

        for(int i = 0; i < itemPrequerisites[subMenuId][id].length; i++) {
            if(!ScoreManager.manager.isItemBought(itemPrequerisites[subMenuId][id][i][0], itemPrequerisites[subMenuId][id][i][1]))
                return false;
        }
        return true;
    }

}
