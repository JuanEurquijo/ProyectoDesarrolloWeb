import { Schedule } from "./schedule";
import { Station } from "./station";

export class Route {
  public id: number | undefined;
  constructor(
    public code?:string,
    public stations?:Station[],
    public schedule?:Schedule) {};
}
