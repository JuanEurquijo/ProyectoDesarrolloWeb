export class Schedule {
  public id: number | undefined;
  constructor(
    public assignedDay?:string,
    public startTime?:Date,
    public endTime?:Date) {};
}
