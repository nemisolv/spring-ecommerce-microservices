import type { NextConfig } from "next";

//  config to load svg files as react components
const svgLoader = {
  loader: '@svgr/webpack',
  options: {
    svgoConfig: {
      plugins: [
        {
          name: 'preset-default',
          params: {
            overrides: {
              removeViewBox: false // Preserve the viewBox attribute
            }
          }
        }
      ]
    }
  }
};

const nextConfig: NextConfig = {
  /* config options here */

  reactStrictMode: true,
  // async rewrites() {
  //   return [
  //     {
  //       source: "/api/v1/:path*",
  //       destination: "http://localhost:8222/api/v1/:path*", // Proxy to Backend
  //     },
  //   ];
    
  // },
  experimental: {
    turbo: {
      rules: {
        '*.svg': {
          loaders: [svgLoader],
          as: '*.js'
        }
      }
    }
  },


  webpack(config) {
    config.module.rules.push({
      test: /\.svg$/i,
      use: [svgLoader]
    });
    return config;
  }
 
};

export default nextConfig;
