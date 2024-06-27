import { defineConfig } from 'vite';

export default defineConfig({
    base: process.env.VITE_BASE_URL || '/',
    build: {
      target: 'esnext',
      outDir: 'dist',
      rollupOptions: {
        output: {
          manualChunks: undefined,
        },
      },
    },
    optimizeDeps: {
      esbuildOptions: {
          target: 'esnext'
      }
    },
    esbuild: {
        supported: {
            'top-level-await': true
        },
    }
})