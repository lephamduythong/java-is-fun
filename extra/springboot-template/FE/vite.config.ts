import { defineConfig } from 'vite';
import angular from '@analogjs/vite-plugin-angular';
import { resolve } from 'path';

export default defineConfig({
  plugins: [
    angular({ tsconfig: resolve(__dirname, 'tsconfig.json') }),
  ],
});
