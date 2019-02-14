'use strict'

const Path = require("path")
const FS = require("fs")
const CWD = FS.realpathSync(process.cwd())
//use this webpack-node-externals to exclude the node dependencies
//node dependenices on backend are placed in node_modules

module.exports = {
    entry: {
        main: Path.resolve(CWD, "src/index.tsx"),
        registration: Path.resolve(CWD, "src/registration.tsx")
    },
    output: {
        filename: "[name].js",
        path: Path.resolve(CWD, "resources/js"),
        publicPath: "/"
    },

    //enable sourcemap for debugging webpack output
    devtool: "source-map",
    target: "web",
    resolve: {
        //Add ".ts", ".tsx" as resolve extensions
        extensions: [".ts", ".tsx", ".js", ".json"]
    },
    module: {
        rules: [
            //All files with a '.ts' or '.tsx' extension will be handled by 'awesome-typescript-loader'
            {
                test: /\.tsx?$/,
                loader: "awesome-typescript-loader",
                // loader: "awesome-typescript-loader?{configFileName: 'config/tsconfig.json'}"
                exclude: [
                    Path.resolve(CWD, "node_modules"),
                ]
            },
            //all output '.js' files will have any sourcemaps reprocessed by 'source-map-loader'.
            { enforce: "pre", test: /\.js$/, loader: "source-map-loader" },
        ]
    },
    externals: {
        "react": "React",
        "react-dom": "ReactDOM",
        // "mobx-react": "MobxReact",
        "mobx": "mobx",
        "antd": "antd"
    }
}