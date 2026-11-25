'use strict';
const chapters = require('./chapters');

function lookup(code) {
  if (!code || !/^[0-9]{4,10}$/.test(code)) return null;
  const chapter = code.slice(0, 2);
  const title = chapters[chapter];
  if (!title) return null;
  return { chapter, heading: code.slice(0, 4), title };
}

module.exports = { lookup, chapters };
