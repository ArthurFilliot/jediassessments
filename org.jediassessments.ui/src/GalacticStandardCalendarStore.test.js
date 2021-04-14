import {galacticStandardCalendarReducer , update} from './GalacticStandardCalendarStore'

function* mockedReponses() {
  yield {dates:[{"key":{"day":1,"period":1,"year":-35},"value":"1618257458000"},{"key":{"day":2,"period":1,"year":-35},"value":"1618257459000"},{"key":{"day":3,"period":1,"year":-35},"value":"1618257460000"},{"key":{"day":4,"period":1,"year":-35},"value":"1618257461000"}]}
  yield {dates:[{"key":{"day":6,"period":1,"year":-35},"value":"1618257463000"},{"key":{"day":7,"period":1,"year":-35},"value":"1618257464000"},{"key":{"day":8,"period":1,"year":-35},"value":"1618257465000"},{"key":{"day":9,"period":1,"year":-35},"value":"1618257466000"},{"key":{"day":10,"period":1,"year":-35},"value":"1618257467000"}]}
  yield {type:'end'};
};
let responsesGen = mockedReponses();

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
    let currClientNow = 1618257458955;

    update(currClientNow, currWindow, true); 
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: currWindow,
      nowIndex: 0,
      running: true,
    });

    currClientNow = 1618257459001;
    update(currClientNow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: currWindow,
      nowIndex: 1, 
      running: true,
    });

    currClientNow = 1618257460000;
    update(currClientNow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: currWindow,
      nowIndex: 2, 
      running: true,
    });

    currClientNow = 1618257464000;
    update(currClientNow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: currWindow,
      nowIndex: 3, 
      running: true,
    });

    currWindow = responsesGen.next().value;
    currClientNow = 1618257464000;
    update(currClientNow, currWindow);
    expect(galacticStandardCalendarReducer(undefined, {}))
    .toEqual({
      galacticWindow: currWindow,
      nowIndex: 1, 
      running: true,
    });

  })
})

