import {galacticStandardCalendarReducer , update} from './GalacticStandardCalendarStore'

function* mockedReponses() {
  yield {"1620074242314":"Atunda 1 Elona -35","1620074243314":"Katunda 2 Elona -35","1620074244314":"Satunda 3 Elona -35","1620074245314":"Datunda 4 Elona -35","1620074246314":"Natunda 5 Elona -35"}
  yield {"1620074247314":"Atunda 6 Elona -35","1620074248314":"Katunda 7 Elona -35","1620074249314":"Satunda 8 Elona -35","1620074250314":"Datunda 9 Elona -35","1620074251314":"Natunda 10 Elona -35"}
  yield {type:'end'};
};
let responsesGen = mockedReponses();

function* mockedReponsesWindowResults() {
  yield {"1620074242314":"Atunda 1 Elona -35","1620074245314":"Datunda 4 Elona -35"}
  yield {"1620074247314":"Atunda 6 Elona -35","1620074250314":"Datunda 9 Elona -35"}
  yield {type:'end'};
};
let responsesWindowsGen = mockedReponsesWindowResults();

describe('galacticStandardCalendarReducer', () => {
  let logSpy
  beforeEach(() => {
    logSpy = jest.spyOn(console, 'debug');
  });
  it('should return the initial state', () => {
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: {},
      nowIndex: -1, 
      running: false,
    });

    let currWindow = responsesGen.next().value;
    let expectedCurrWindow = responsesWindowsGen.next().value;
    let currClientNow = 1620074242314;

    update(currClientNow, currWindow, true); 
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: expectedCurrWindow,
      nowIndex: 0,
      running: true,
    });

    currClientNow = currClientNow + 1000;
    update(currClientNow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: expectedCurrWindow,
      nowIndex: 1, 
      running: true,
    });

    currClientNow = currClientNow + 1000;
    update(currClientNow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: expectedCurrWindow,
      nowIndex: 1, 
      running: true,
    });

    currClientNow = currClientNow + 1000;
    update(currClientNow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: expectedCurrWindow,
      nowIndex: 1, 
      running: true,
    });

    currClientNow = currClientNow + 1000;
    update(currClientNow, currWindow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: expectedCurrWindow,
      nowIndex: 1, 
      running: true,
    });

    currWindow = responsesGen.next().value;
    expectedCurrWindow = responsesWindowsGen.next().value;
    currClientNow = currClientNow + 1000;
    update(currClientNow, currWindow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: expectedCurrWindow,
      nowIndex: 0, 
      running: true,
    });

  })
})