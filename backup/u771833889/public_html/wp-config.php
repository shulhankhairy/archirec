<?php
/**
 * The base configuration for WordPress
 *
 * The wp-config.php creation script uses this file during the
 * installation. You don't have to use the web site, you can
 * copy this file to "wp-config.php" and fill in the values.
 *
 * This file contains the following configurations:
 *
 * * MySQL settings
 * * Secret keys
 * * Database table prefix
 * * ABSPATH
 *
 * @link https://codex.wordpress.org/Editing_wp-config.php
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define('DB_NAME', 'u771833889_logis');

/** MySQL database username */
define('DB_USER', 'u771833889_ulog');

/** MySQL database password */
define('DB_PASSWORD', 'xHNYJRe3qH7D');

/** MySQL hostname */
define('DB_HOST', 'localhost');

/** Database Charset to use in creating database tables. */
define('DB_CHARSET', 'utf8');

/** The Database Collate type. Don't change this if in doubt. */
define('DB_COLLATE', '');

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define('AUTH_KEY',         'K9zbg7v.LzDIfPH>)Yh .;2kEr`+JZR=mJ,R7?(l6o#v2C>n$-+y--76wu39W1U=');
define('SECURE_AUTH_KEY',  '+r*wDrIIMogbU=zJ^:6u2axr1dTLUHXH&XPUzZ&cqHJzBydZ/Dq1k)A=qKL2OFbx');
define('LOGGED_IN_KEY',    'YHY?@LQBp]Y{4K}#i?,JGw0Ee39SmX{y,[nP5LaM.(`,rPuXpOIfVU|6i4=|(LyX');
define('NONCE_KEY',        '`kBNUoA3p@w)]9h]!D&h)< F5x}/CEH]#<b2v)GjCjo9AcngmSUj49@c{KbR2b-y');
define('AUTH_SALT',        '_[^DW*/bE=mB-=in DkEa)m?gn/S_-rq&P.]k_|wJO[_P`yD;PdtI}#TUK-lh+AM');
define('SECURE_AUTH_SALT', 's=9#T2jAME*T?/0l2gS_G?dg4XI w(P5@%vh5KRjH#33AEqe*+$U?kw:T:d3l?T-');
define('LOGGED_IN_SALT',   '!k|@Ey%.c+=#Wf`=[6W{8h{W!-Y1K6#D^uI:3kD]!U+2Jmagf~8|CLG}5qi_!nP<');
define('NONCE_SALT',       'le0Wb rqUv%Qklbc6EhO]T|~#Do].ubqS+*qTS07{d#M[u&f= dR7Cur_~-@#vdw');
/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each
 * a unique prefix. Only numbers, letters, and underscores please!
 */
$table_prefix  = 'wp_';

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 *
 * For information on other constants that can be used for debugging,
 * visit the Codex.
 *
 * @link https://codex.wordpress.org/Debugging_in_WordPress
 */
define('WP_DEBUG', false);
define('WP_HOME','http://www.logiswebmedia.com');
define('WP_SITEURL','http://www.logiswebmedia.com');

/* That's all, stop editing! Happy blogging. */

/** Absolute path to the WordPress directory. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Sets up WordPress vars and included files. */
require_once(ABSPATH . 'wp-settings.php');
